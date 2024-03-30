package test.org.fugerit.java.nhg;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.nhg.GenerateReflectConfig;
import org.fugerit.java.nhg.config.NativeHelperFacade;
import org.fugerit.java.nhg.config.model.NativeHelperConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

@Slf4j
class TestNativeHelperFacade {

    private NativeHelperConfig loadConfig(String path ) {
        NativeHelperConfig config = NativeHelperFacade.loadConfig( path );
        config.getGenerate().forEach( g -> log.info( "generation config : {}", g ) );
        return config;
    }

    private void printConfig( NativeHelperConfig config ) {
        GenerateReflectConfig generateReflectConfig = new GenerateReflectConfig();
        try ( PrintWriter writer = new PrintWriter( System.out ) ) {
            generateReflectConfig.generate( writer, NativeHelperFacade.generateEntries( config ) );
        }
    }

    @Test
    void testConfig1() {
        NativeHelperConfig config = loadConfig( "src/test/resources/tool/config/native-helper-config-1.yaml" );
        Assertions.assertNotNull( config );
        this.printConfig( config );
    }

    @Test
    void testConfig2() {
        NativeHelperConfig config = loadConfig( "src/test/resources/tool/config/native-helper-config-2.yaml" );
        Assertions.assertNotNull( config );
        this.printConfig( config );
    }

}
