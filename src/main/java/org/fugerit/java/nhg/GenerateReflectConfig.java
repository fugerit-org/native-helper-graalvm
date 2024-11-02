package org.fugerit.java.nhg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.nhg.reflect.config.Entry;
import org.fugerit.java.nhg.reflect.config.EntryField;
import org.fugerit.java.nhg.reflect.config.EntryMethod;

import java.io.Writer;
import java.util.*;

public class GenerateReflectConfig {

    private ObjectWriter writer;

    public GenerateReflectConfig(ObjectWriter writer) {
        this.writer = writer;
    }

    public GenerateReflectConfig() {
        this( new ObjectMapper().setSerializationInclusion( JsonInclude.Include.NON_NULL ).writerWithDefaultPrettyPrinter() );
    }

    private static String keyEntryField( EntryField o ) {
        return o.getName();
    }

    private static String keyEntryMethod( EntryMethod o ) {
        return o.getName()+"-"+ StringUtils.concat( ",", o.getParameterTypes() );
    }

    public void normalizeSort( List<Entry> reflectConfig ) {
        for ( Entry e : reflectConfig ) {
            if ( e.getFields() != null ) {
                Collections.sort( e.getFields(), Comparator.comparing(GenerateReflectConfig::keyEntryField));
            }
            if ( e.getMethods() != null ) {
                Collections.sort( e.getMethods(), Comparator.comparing(GenerateReflectConfig::keyEntryMethod));
            }
        }
    }

    public void generate(Writer out, List<Entry> reflectConfig ) {
        SafeFunction.apply( () -> {
            normalizeSort( reflectConfig );
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            writer.writeValue( out, reflectConfig );
        } );
    }

}
