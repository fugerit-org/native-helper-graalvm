package org.fugerit.java.nhg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.nhg.reflect.config.Entry;

import java.io.Writer;
import java.util.List;

public class GenerateReflectConfig {

    private ObjectWriter writer;

    public GenerateReflectConfig(ObjectWriter writer) {
        this.writer = writer;
    }

    public GenerateReflectConfig() {
        this( new ObjectMapper().setSerializationInclusion( JsonInclude.Include.NON_NULL ).writerWithDefaultPrettyPrinter() );
    }

    public void generate(Writer out, List<Entry> reflectConfig ) {
        SafeFunction.apply( () -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            writer.writeValue( out, reflectConfig );
        } );
    }

}
