package org.fugerit.java.nhg.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.io.helper.StreamHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.util.ObjectUtils;
import org.fugerit.java.nhg.ReflectConfigUtil;
import org.fugerit.java.nhg.config.model.NativeHelperConfig;
import org.fugerit.java.nhg.reflect.config.Entry;
import org.fugerit.java.nhg.reflect.config.EntryHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NativeHelperFacade {

    private NativeHelperFacade() {}

    public static final String MODE_GETTERS = "getters";

    public static final String MODE_SETTERS = "setters";

    public static final String MODE_GETTERS_SETTERS = MODE_GETTERS+"_"+MODE_SETTERS;

    public static final String MODE_ALL = "all";

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

    public static List<Entry> generateEntries( NativeHelperConfig config ) {
        final List<Entry> list = new ArrayList<>();
        config.getGenerate().forEach(g -> {
            if (StringUtils.isNotEmpty(g.getClassName())) {
                SafeFunction.apply( () -> {
                    Class<?> c = Class.forName(g.getClassName());
                    ReflectConfigUtil utils = getUtils( g.getMode() );
                    Entry entry = utils.toEntry( c );
                    if ( g.isConstructors() ) {
                        EntryHelper.addDefaultInit( entry );
                    }
                    EntryHelper.fixedOrder( entry );
                    list.add(entry);
                } );
            }
        });
        return list;
    }

}
