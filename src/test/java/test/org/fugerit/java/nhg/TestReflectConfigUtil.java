package test.org.fugerit.java.nhg;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.nhg.GenerateReflectConfig;
import org.fugerit.java.nhg.ReflectConfigUtil;
import org.fugerit.java.nhg.reflect.config.Entry;
import org.fugerit.java.nhg.reflect.config.EntryHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

@Slf4j
class TestReflectConfigUtil {

    Entry testReflectConfigUtilWorker( ReflectConfigUtil reflectConfigUtil ) throws IOException {
        ReflectDemo demo = new ReflectDemo();
        Entry entry = reflectConfigUtil.toEntry( demo.getClass() );
        EntryHelper.addInit( EntryHelper.addDefaultInit( entry ), Arrays.asList( String.class.getName() ) );
        EntryHelper.fixedOrder( entry );
        Assertions.assertEquals( demo.getClass().getName(), entry.getName() );
        GenerateReflectConfig config = new GenerateReflectConfig();
        try (StringWriter writer = new StringWriter() ) {
            config.generate( writer, Arrays.asList( entry ) );
            log.info( "testReflectUtil : {}", writer.toString() );
        }
        return entry;
    }

    @Test
    void testReflectUtilAllMethods() throws IOException {
        Entry entry = this.testReflectConfigUtilWorker( ReflectConfigUtil.ALL_METHODS );
        Assertions.assertNotNull( entry );
    }

    @Test
    void testReflectUtilDeclaredMethods() throws IOException {
        Assertions.assertNotNull( this.testReflectConfigUtilWorker( ReflectConfigUtil.DECLARED_METHODS ) );
    }

    @Test
    void testReflectUtilGettersSetters() throws IOException {
        Assertions.assertNotNull( this.testReflectConfigUtilWorker( ReflectConfigUtil.GETTERS_SETTERS ) );
    }
    @Test
    void testReflectUtilGettersOnly() throws IOException {
        Assertions.assertNotNull( this.testReflectConfigUtilWorker( ReflectConfigUtil.GETTERS_ONLY ) );
    }

    @Test
    void testReflectUtilSettersOnly() throws IOException {
        Assertions.assertNotNull( this.testReflectConfigUtilWorker( ReflectConfigUtil.SETTERS_ONLY ) );
    }

}
