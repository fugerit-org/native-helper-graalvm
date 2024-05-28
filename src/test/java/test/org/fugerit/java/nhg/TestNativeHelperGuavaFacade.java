package test.org.fugerit.java.nhg;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.nhg.config.NativeHelperFacade;
import org.fugerit.java.nhg.config.model.NativeHelperConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Slf4j
class TestNativeHelperGuavaFacade {

    private static final String BASE_TEST_CONFIG_PATH = "src/test/resources/tool/config/";

    private static final String TEST_CONFIG_GUAVA = BASE_TEST_CONFIG_PATH+"native-helper-config-guava.yaml";

    @Test
    void testConfigGuava() throws IOException {
        NativeHelperConfig config = NativeHelperFacade.loadAndGenerate( TEST_CONFIG_GUAVA );
        Assertions.assertNotNull( config );
    }

}
