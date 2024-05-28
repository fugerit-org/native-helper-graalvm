package test.org.fugerit.java.nhg;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.nhg.GenerateReflectConfig;
import org.fugerit.java.nhg.config.NativeHelperFacade;
import org.fugerit.java.nhg.config.model.NativeHelperConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
class TestNativeHelperFacade {

    private static final String BASE_TEST_CONFIG_PATH = "src/test/resources/tool/config/";

    private static final String TEST_CONFIG_1 = BASE_TEST_CONFIG_PATH+"native-helper-config-1.yaml";

    private static final String TEST_CONFIG_2 = BASE_TEST_CONFIG_PATH+"native-helper-config-2.yaml";

    private static final String TEST_CONFIG_MODE_DOES_NOT_EXIST = BASE_TEST_CONFIG_PATH+"native-helper-config-mode-does-not-exist.yaml";

    private static final String TEST_CONFIG_BLANK = BASE_TEST_CONFIG_PATH+"native-helper-config-blank.yaml";

    private static final String TEST_CONFIG_PACKAGE = BASE_TEST_CONFIG_PATH+"native-helper-config-package.yaml";

    private static final String TEST_CONFIG_GUAVA = BASE_TEST_CONFIG_PATH+"native-helper-config-guava.yaml";

    private NativeHelperConfig loadConfig(String path ) {
        NativeHelperConfig config = NativeHelperFacade.loadConfig( path );
        config.getGenerate().forEach( g -> log.info( "generation config : {}", g ) );
        log.info( "config : {}", config );
        return config;
    }

    private void printConfig( NativeHelperConfig config ) {
        GenerateReflectConfig generateReflectConfig = new GenerateReflectConfig();
        try ( PrintWriter writer = new PrintWriter( System.out ) ) {
            generateReflectConfig.generate( writer, NativeHelperFacade.generateEntries( config ) );
        }
    }

    private Map<String, Boolean> CONFIG_MAP = Stream.of(
                    new AbstractMap.SimpleImmutableEntry<>(TEST_CONFIG_1, true),
                    new AbstractMap.SimpleImmutableEntry<>(TEST_CONFIG_2, false),
                    new AbstractMap.SimpleImmutableEntry<>(TEST_CONFIG_MODE_DOES_NOT_EXIST, false),
                    new AbstractMap.SimpleImmutableEntry<>(TEST_CONFIG_PACKAGE, true),
                    new AbstractMap.SimpleImmutableEntry<>(TEST_CONFIG_BLANK, true))
            .           collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    @Test
    void testConfig() {
        CONFIG_MAP.forEach( ( k, v ) -> {
            log.info( " test {} - {} ", k, v );
            NativeHelperConfig config = this.loadConfig( k );
            Assertions.assertNotNull( config );
            if ( v ) {
                this.printConfig( config );
            } else {
               Assertions.assertThrows(ConfigRuntimeException.class, () -> this.printConfig( config ) );
            }
        } );
    }

    @Test
    void testConfig1() throws IOException {
        Assertions.assertThrows( IOException.class, () -> NativeHelperFacade.loadAndGenerate( TEST_CONFIG_1 ) );
        File file = new File( "target/native-image" );
        file.mkdir();
        NativeHelperFacade.loadAndGenerate( TEST_CONFIG_1 );
    }

    @Test
    void testConfigFail2() {
        Assertions.assertThrows( ConfigRuntimeException.class, () -> NativeHelperFacade.loadAndGenerate( TEST_CONFIG_2 ) );
    }

}
