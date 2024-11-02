package test.org.fugerit.java.nhg;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.nhg.GenerateReflectConfig;
import org.fugerit.java.nhg.MergeConfigUtil;
import org.fugerit.java.nhg.reflect.config.Entry;
import org.fugerit.java.nhg.reflect.config.EntryField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
class TestMergeConfigUtil {

    private static final String TEST_PATH = "src/test/resources/reflect-config";

    private static final String TEST_REFLECT_CONFIG_1 = TEST_PATH+"/reflect-config-emp.json";

    private static final String TEST_REFLECT_CONFIG_2 = TEST_PATH+"/reflect-config-nhg.json";

    @Test
    void merge1() throws IOException {
        List<Entry> entries = new ArrayList<>();
        MergeConfigUtil.add( entries, new File( TEST_REFLECT_CONFIG_1 ) );
        MergeConfigUtil.add( entries, new File( TEST_REFLECT_CONFIG_2 ) );
        GenerateReflectConfig generateReflectConfig = new GenerateReflectConfig();
        try (StringWriter writer = new StringWriter()) {
            generateReflectConfig.generate( writer, entries );
            log.info( "print test result 1 : {}", writer );
        }
        Assertions.assertFalse( entries.isEmpty() );
    }

    @Test
    void testSort() {
        Entry entry1 = new Entry();
        entry1.setFields(Arrays.asList( new EntryField( "id" )));
        List<Entry> entries = Arrays.asList( entry1 );
        GenerateReflectConfig generateReflectConfig = new GenerateReflectConfig();
        generateReflectConfig.normalizeSort( entries );
        Assertions.assertFalse( entries.isEmpty() );
    }

    @Test
    void merge2() throws IOException {
        List<Entry> entries = new ArrayList<>();
        MergeConfigUtil.addFolder( entries, new File( TEST_PATH ) );
        GenerateReflectConfig generateReflectConfig = new GenerateReflectConfig();
        try (StringWriter writer = new StringWriter()) {
            generateReflectConfig.generate( writer, entries );
            log.info( "print test result 2 : {}", writer );
        }
        Assertions.assertFalse( entries.isEmpty() );
    }

    @Test
    void mergeFail() {
        File file = new File( TEST_REFLECT_CONFIG_1 );
        List<Entry> entries = new ArrayList<>();
        Assertions.assertThrows( ConfigRuntimeException.class, () -> MergeConfigUtil.addFolder( entries, file ) );
    }

}
