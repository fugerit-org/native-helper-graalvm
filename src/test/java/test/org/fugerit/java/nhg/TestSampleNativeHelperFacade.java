package test.org.fugerit.java.nhg;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.nhg.GenerateReflectConfig;
import org.fugerit.java.nhg.config.NativeHelperFacade;
import org.fugerit.java.nhg.config.model.NativeHelperConfig;
import org.fugerit.java.nhg.reflect.config.Entry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Slf4j
class TestSampleNativeHelperFacade {

    @Test
    void nativeHelperFacadeSample() throws IOException {
        // path the native-helper-config.yaml file
        String path = "src/test/resources/tool/config/native-helper-config-1.yaml";
        // reads configuration
        NativeHelperConfig config = NativeHelperFacade.loadConfig( path );
        // creates entries from configuration
        List<Entry> entries = NativeHelperFacade.generateEntries( config );
        // print the result
        GenerateReflectConfig generateReflectConfig = new GenerateReflectConfig();
        try (StringWriter writer = new StringWriter() ) {
            generateReflectConfig.generate( writer, entries );
            log.info( "print reflect config : \n{}", writer.toString() );
        }
        // test the result
        Assertions.assertEquals( 4, entries.size() );
    }

}
