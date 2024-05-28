package test.org.fugerit.java.nhg;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.nhg.config.NativeHelperFacade;
import org.fugerit.java.nhg.config.model.NativeHelperConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
@Slf4j
class TestNativeHelperMergeFacade {

    private static final String BASE_TEST_CONFIG_PATH = "src/test/resources/tool/config/";

    private static final String TEST_CONFIG_MERGE_1 = BASE_TEST_CONFIG_PATH+"native-helper-config-merge-1.yaml";

    private static final String TEST_CONFIG_MERGE_2 = BASE_TEST_CONFIG_PATH+"native-helper-config-merge-2.yaml";

    @Test
    void testConfigMerge1() throws IOException {
        NativeHelperConfig config = NativeHelperFacade.loadAndGenerate( TEST_CONFIG_MERGE_1 );
        Assertions.assertNotNull( config );
    }

    @Test
    void testConfigMerge2() throws IOException {
        NativeHelperConfig config = NativeHelperFacade.loadAndGenerate( TEST_CONFIG_MERGE_2 );
        Assertions.assertNotNull( config );
    }

}
