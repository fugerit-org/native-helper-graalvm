package test.org.fugerit.java.nhg;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.nhg.GenerateReflectConfig;
import org.fugerit.java.nhg.config.NativeHelperFacade;
import org.fugerit.java.nhg.config.model.NativeHelperConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
class TestNativeHelperFacade {

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
                    new AbstractMap.SimpleImmutableEntry<>("src/test/resources/tool/config/native-helper-config-1.yaml", true),
                    new AbstractMap.SimpleImmutableEntry<>("src/test/resources/tool/config/native-helper-config-2.yaml", false),
                    new AbstractMap.SimpleImmutableEntry<>("src/test/resources/tool/config/native-helper-config-blank.yaml", true))
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

}
