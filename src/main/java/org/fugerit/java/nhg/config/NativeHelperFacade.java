package org.fugerit.java.nhg.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.io.helper.StreamHelper;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.util.ObjectUtils;
import org.fugerit.java.nhg.GenerateReflectConfig;
import org.fugerit.java.nhg.ReflectConfigUtil;
import org.fugerit.java.nhg.config.model.GenerateConfig;
import org.fugerit.java.nhg.config.model.NativeHelperConfig;
import org.fugerit.java.nhg.reflect.config.Entry;
import org.fugerit.java.nhg.reflect.config.EntryCondition;
import org.fugerit.java.nhg.reflect.config.EntryHelper;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
public class NativeHelperFacade {

    private NativeHelperFacade() {}

    public static final String MODE_GETTERS = "getters";

    public static final String MODE_SETTERS = "setters";

    public static final String MODE_GETTERS_SETTERS = MODE_GETTERS+"_"+MODE_SETTERS;

    public static final String MODE_ALL = "all";

    public static final String PARAM_REFLECT_CONFIG_JSON_OUTPUT_PATH = "reflectConfigJsonOutputPath";

    public static final String MERGE_MODE_FAIL_ON_ERROR = "failOnError";

    public static final String MERGE_MODE_WARN_ON_ERROR = "warnOnError";

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    public static NativeHelperConfig loadConfig( String path ) {
        return SafeFunction.get( () -> {
            try (InputStream is = StreamHelper.resolveStream( path )) {
                ObjectMapper mapper = new YAMLMapper();
                NativeHelperConfig config = ObjectUtils.objectWithDefault(
                        mapper.readValue( is, NativeHelperConfig.class ), new NativeHelperConfig() );
                if ( config.getGenerate() == null ) {
                    config.setGenerate( new ArrayList<>() );
                }
                return config;
            }
        } );
    }

    private static ReflectConfigUtil getUtils( String mode ) {
        switch (mode) {
            case MODE_GETTERS:
                return ReflectConfigUtil.GETTERS_ONLY;
            case MODE_SETTERS:
                return ReflectConfigUtil.SETTERS_ONLY;
            case MODE_GETTERS_SETTERS:
                return ReflectConfigUtil.GETTERS_SETTERS;
            case MODE_ALL:
                return ReflectConfigUtil.ALL_METHODS;
            default:
                throw new ConfigRuntimeException(String.format("Mode not found %s", mode));
        }
    }

    private static void handleClass( final Class<?> c, final List<Entry> list, final GenerateConfig g ) {
        ReflectConfigUtil utils = getUtils( g.getMode() );
        Entry entry = utils.toEntry( c, !g.isSkipConstructors() );
        if ( StringUtils.isNotEmpty( g.getTypeReachable() ) ) {
            entry.setCondition( new EntryCondition( g.getTypeReachable() ) );
        }
        EntryHelper.fixedOrder( entry );
        list.add(entry);
    }

    private static void merge( NativeHelperConfig config, final List<Entry> list ) {
        SafeFunction.applyIfNotNull( config.getMerge(), () -> config.getMerge().forEach( m -> {
            Consumer<Exception> exceptionConsumer = MERGE_MODE_FAIL_ON_ERROR.equalsIgnoreCase( m.getMode() ) ? SafeFunction.EX_CONSUMER_THROW_CONFIG_RUNTIME : SafeFunction.EX_CONSUMER_TRACE_WARN;
            SafeFunction.apply( () -> {
                log.info( "merge config : {}", m );
                try ( FileReader fis = new FileReader( m.getReflectConfigPath() ) ) {
                    list.addAll( JSON_MAPPER.readValue( fis, new TypeReference<List<Entry>>() {} ) );
                }
            }, exceptionConsumer );
        } ) );
    }

    private static Collection<Class<?>> lookup( NativeHelperConfig config, GenerateConfig g ) {
        log.info( "lookup package : {}", g.getPackageName() );
        return SafeFunction.get( () -> {
            if ( config.isJarPackageDiscovery() || g.isJarPackageDiscovery() ) {
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                ClassPath cp = ClassPath.from( cl );
                ImmutableSet<ClassPath.ClassInfo> allClasses = cp.getTopLevelClasses( g.getPackageName() );
                return allClasses.stream().map( ci ->  SafeFunction.get( () -> cl.loadClass( ci.getName() ) ) ).collect( Collectors.toList() );
            } else {
                return PackageLookupHelper.findAllClassesUsingClassLoader( g.getPackageName() );
            }
        });
    }

    public static List<Entry> generateEntries( NativeHelperConfig config ) {
        final List<Entry> list = new ArrayList<>();
        config.getGenerate().forEach(g -> {
            if (StringUtils.isNotEmpty(g.getClassName())) {
                Class<?> c = SafeFunction.get( () -> ClassHelper.getDefaultClassLoader().loadClass(g.getClassName() ) );
                log.info( "generate class reflect config : {}", c );
                handleClass( c, list, g );
            } else if ( StringUtils.isNotEmpty(g.getPackageName()) ) {
                Set<String> excludeClassNames = new HashSet<>();
                if ( StringUtils.isNotEmpty( g.getExcludeClassNames() ) ) {
                    excludeClassNames.addAll( Arrays.asList( g.getExcludeClassNames().split( "," ) ) );
                }
                lookup( config, g ).stream()
                        .filter(
                            c -> !excludeClassNames.contains( c.getSimpleName() )
                        ).sorted(
                            Comparator.comparing( Class::getSimpleName )
                        ).forEach( c -> {
                            log.info( "generate class reflect config : {} (from package : {})", c, g.getPackageName() );
                            handleClass( c, list, g );
                        } );
            } else {
                throw new ConfigRuntimeException( "className or packageName must be provided for each entry" );
            }
        });
        merge( config, list );
        return list;
    }

    public static List<Entry> generate( NativeHelperConfig config, File file ) throws IOException {
        try ( FileWriter writer = new FileWriter( file ) ) {
            return generate( config, writer );
        }
    }

    public static List<Entry> generate( NativeHelperConfig config, Writer reflectConfigJsonWriter ) {
        List<Entry> entries = generateEntries( config );
        GenerateReflectConfig generateReflectConfig = new GenerateReflectConfig();
        generateReflectConfig.generate( reflectConfigJsonWriter, entries );
        return  entries;
    }

    public static NativeHelperConfig loadAndGenerate(String nativeHelperConfigPath ) throws IOException {
        NativeHelperConfig config = loadConfig( nativeHelperConfigPath );
        String reflectConfigJsonOutputPath = config.getReflectConfigJsonOutputPath();
        if ( StringUtils.isNotEmpty( reflectConfigJsonOutputPath ) ) {
            File reflectConfigJsonFile = new File( reflectConfigJsonOutputPath );
            if ( config.isCreateParentDirectory() ) {
                log.info( "createParentDirectory : {} -> {}", reflectConfigJsonFile.getParentFile(), reflectConfigJsonFile.getParentFile().mkdirs() );
            }
            generate( config, reflectConfigJsonFile );
        } else {
            throw new ConfigRuntimeException( String.format( "Param %s not set.", PARAM_REFLECT_CONFIG_JSON_OUTPUT_PATH ) );
        }
        return config;
    }

}
