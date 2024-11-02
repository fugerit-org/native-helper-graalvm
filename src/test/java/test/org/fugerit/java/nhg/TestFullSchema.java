package test.org.fugerit.java.nhg;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.nhg.JacksonHelper;
import org.fugerit.java.nhg.reflect.config.Entry;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

@Slf4j
class TestFullSchema {

    @Test
    void loadWriteFullSchema() throws IOException {
        try (InputStreamReader reader = new InputStreamReader(ClassHelper.loadFromDefaultClassLoader( "schema/full_schema.json" ));
             StringWriter writer = new StringWriter()) {
            ObjectMapper mapper = JacksonHelper.newObjectMapper();
            Entry entry = mapper.readValue( reader, Entry.class );
            mapper.writerWithDefaultPrettyPrinter().writeValue( writer, entry );
            log.info( "json result : {}", writer );
        }
    }

}
