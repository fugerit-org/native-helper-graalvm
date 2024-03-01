package test.org.fugerit.java.nhg;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.nhg.GenerateReflectConfig;
import org.fugerit.java.nhg.reflect.config.Entry;
import org.fugerit.java.nhg.reflect.config.EntryCondition;
import org.fugerit.java.nhg.reflect.config.EntryField;
import org.fugerit.java.nhg.reflect.config.EntryMethod;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
public class TestGenerateReflectConfig {

    @Test
    public void testKo() {
        Assert.assertThrows( NullPointerException.class, () -> { new Entry( null ); } );
        Assert.assertThrows( NullPointerException.class, () -> { new EntryField( null ); } );
        Assert.assertThrows( NullPointerException.class, () -> { new EntryMethod( null ); } );
        final Entry entry = new Entry();
        Assert.assertThrows( NullPointerException.class, () -> { entry.setName( null ); } );
        final EntryField field = new EntryField();
        Assert.assertThrows( NullPointerException.class, () -> { field.setName( null ); } );
        final EntryMethod method = new EntryMethod();
        Assert.assertThrows( NullPointerException.class, () -> { method.setName( null ); } );
    }

    @Test
    public void testGenerate() throws IOException {
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
        // generate
        GenerateReflectConfig gen = new GenerateReflectConfig();
        try (StringWriter writer = new StringWriter()) {
            gen.generate( writer, Arrays.asList( entry1, entry2 ) );
            log.info( "output : \n{}", writer.toString() );
        }
    }

}
