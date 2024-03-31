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
class TestSampleReflectConfigUtil {

    @Test
    void reflectConfigSample() throws IOException {
        Class<?> c = ReflectDemo.class;
        // choose ReflectConfigUtil or define a custom one
        // in this example we will render only getters methods
        ReflectConfigUtil reflectConfigUtil = ReflectConfigUtil.GETTERS_ONLY;
        // creates new reflect config entry, with selected methods and all constructors
        Entry entry = reflectConfigUtil.toEntryWithConstructors( c );
        // order methods, constructors first, then methods in alphabetical order
        EntryHelper.fixedOrder( entry );
        // print the result
        GenerateReflectConfig config = new GenerateReflectConfig();
        try (StringWriter writer = new StringWriter() ) {
            config.generate( writer, Arrays.asList( entry ) );
            log.info( "print reflect config : \n{}", writer );
        }
        // test the result
        Assertions.assertEquals( c.getName(), entry.getName() );
    }

}
