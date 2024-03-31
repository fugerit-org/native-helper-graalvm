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
class TestGenerateLibraryConfiguration {

    private static final String NATIVE_HELPER_CONFIG_PATH = "src/main/config/native-helper-config.yaml";

    @Test
    void generateLibraryConfiguration() throws IOException {
        // reads configuration
        NativeHelperConfig config = NativeHelperFacade.loadAndGenerate( NATIVE_HELPER_CONFIG_PATH );
        Assertions.assertNotNull( config );
    }

}
