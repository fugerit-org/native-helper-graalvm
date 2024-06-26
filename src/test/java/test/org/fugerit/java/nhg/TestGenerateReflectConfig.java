package test.org.fugerit.java.nhg;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.nhg.GenerateReflectConfig;
import org.fugerit.java.nhg.ReflectConfigUtil;
import org.fugerit.java.nhg.reflect.config.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
class TestGenerateReflectConfig {

    @Test
    void testKo() {
        Assertions.assertThrows( NullPointerException.class, () -> { new Entry( null ); } );
        Assertions.assertThrows( NullPointerException.class, () -> { new EntryField( null ); } );
        Assertions.assertThrows( NullPointerException.class, () -> { new EntryMethod( null ); } );
        final Entry entry = new Entry();
        Assertions.assertThrows( NullPointerException.class, () -> { entry.setName( null ); } );
        final EntryField field = new EntryField();
        Assertions.assertThrows( NullPointerException.class, () -> { field.setName( null ); } );
        final EntryMethod method = new EntryMethod();
        Assertions.assertThrows( NullPointerException.class, () -> { method.setName( null ); } );
    }

    @Test
    void testGenerate() throws IOException {
        // entry setup type 1
        Entry entry1 = new Entry( String.class.getName() );
        entry1.setCondition( new EntryCondition( String.class.getName() ));
        entry1.setFields( Arrays.asList( new EntryField( "prop1" ) ) );
        entry1.setMethods( Arrays.asList( new EntryMethod( "test1" ) ) );
        // entry setup type 2
        EntryCondition condition2 = new EntryCondition();
        condition2.setTypeReachable( Double.class.getName() );
        EntryField field2 = new EntryField();
        field2.setName( "prop2" );
        EntryMethod method2 = new EntryMethod();
        method2.setName( "test2" );
        method2.setParameterTypes( Arrays.asList(Date.class.getName()) );
        Entry entry2 = new Entry();
        entry2.setCondition( condition2 );
        entry2.setFields( Arrays.asList( field2 ) );
        entry2.setMethods( Arrays.asList( method2 ) );
        entry2.setName( Integer.class.getName() );
        // auto generate entry from class :
        Entry entry3 = EntryHelper.addDefaultInit( ReflectConfigUtil.GETTERS_ONLY.toEntry( EntryMethod.class ) );
        // generate
        GenerateReflectConfig gen = new GenerateReflectConfig();
        try (StringWriter writer = new StringWriter()) {
            List<Entry> entries = Arrays.asList( entry1, entry2, entry3 );
            // if you want the methods in fixed order :
            entries.forEach( EntryHelper::fixedOrder );
            // generate reflect-config.json
            gen.generate( writer, entries);
            log.info( "output : \n{}", writer.toString() );
        }
    }

}
