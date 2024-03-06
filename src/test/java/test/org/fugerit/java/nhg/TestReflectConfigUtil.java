package test.org.fugerit.java.nhg;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.nhg.GenerateReflectConfig;
import org.fugerit.java.nhg.ReflectConfigUtil;
import org.fugerit.java.nhg.reflect.config.Entry;
import org.fugerit.java.nhg.reflect.config.EntryField;
import org.fugerit.java.nhg.reflect.config.EntryMethod;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

@Slf4j
public class TestReflectConfigUtil {

    public Entry testReflectConfigUtilWorker( ReflectConfigUtil reflectConfigUtil ) throws IOException {
        ReflectDemo demo = new ReflectDemo();
        Entry entry = reflectConfigUtil.toEntry( demo.getClass() );
        Assert.assertEquals( demo.getClass().getName(), entry.getName() );
        GenerateReflectConfig config = new GenerateReflectConfig();
        try (StringWriter writer = new StringWriter() ) {
            config.generate( writer, Arrays.asList( entry ) );
            log.info( "testReflectUtil : {}", writer.toString() );
        }
        return entry;
    }

    @Test
    public void testReflectUtilAllMethods() throws IOException {
        Assert.assertNotNull( this.testReflectConfigUtilWorker( ReflectConfigUtil.ALL_METHODS ) );
    }

    @Test
    public void testReflectUtilDeclaredMethods() throws IOException {
        Assert.assertNotNull( this.testReflectConfigUtilWorker( ReflectConfigUtil.DECLARED_METHODS ) );
    }

    @Test
    public void testReflectUtilGettersSetters() throws IOException {
        Assert.assertNotNull( this.testReflectConfigUtilWorker( ReflectConfigUtil.GETTERS_SETTERS ) );
    }
    @Test
    public void testReflectUtilGettersOnly() throws IOException {
        Assert.assertNotNull( this.testReflectConfigUtilWorker( ReflectConfigUtil.GETTERS_ONLY ) );
    }

    @Test
    public void testReflectUtilSettersOnly() throws IOException {
        Assert.assertNotNull( this.testReflectConfigUtilWorker( ReflectConfigUtil.SETTERS_ONLY ) );
    }

}
