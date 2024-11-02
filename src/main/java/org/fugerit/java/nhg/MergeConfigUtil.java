package org.fugerit.java.nhg;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.nhg.reflect.config.Entry;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeConfigUtil {

    private MergeConfigUtil() {}

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void addFolder( List<Entry> entries, File reflectConfigFolder) {
        if ( reflectConfigFolder.isDirectory() ) {
            Arrays.stream(reflectConfigFolder.listFiles( f -> f.getName().endsWith( "json" ) ))
                    .sorted(Comparator.comparing(File::getName))
                    .forEach(f -> add(entries, f));
        } else {
            throw new ConfigRuntimeException( String.format( "Must be a folder : %s", reflectConfigFolder ) );
        }
    }

    public static void add( List<Entry> entries, File reflectConfigFile) {
        SafeFunction.apply( () -> {
            try (FileReader reflectConfigReader = new FileReader( reflectConfigFile ) ) {
                add( entries, reflectConfigReader );
            }
        } );
    }

    public static void add( List<Entry> entries, Reader reflectConfigReader) {
        SafeFunction.apply( () -> {
            List<Entry> addEntries = MAPPER.readValue( reflectConfigReader, new TypeReference<List<Entry>>() {} );
            entries.addAll( addEntries );
        } );
    }

}
