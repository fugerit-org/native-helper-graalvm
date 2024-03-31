package org.fugerit.java.nhg;

import org.fugerit.java.nhg.reflect.config.Entry;
import org.fugerit.java.nhg.reflect.config.EntryHelper;
import org.fugerit.java.nhg.reflect.config.EntryMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ReflectConfigUtil {

    private Function<Class<?>, List<Method>> fun;

    public ReflectConfigUtil(Function<Class<?>, List<Method>> fun) {
        this.fun = fun;
    }

    public ReflectConfigUtil() {
        this.fun = c -> Arrays.asList( c.getMethods() );
    }

    private EntryMethod newMethod( final String name, final Class<?>[] parameters ) {
        final EntryMethod entryMethod = new EntryMethod( name );
        Arrays.stream( parameters ).forEach( p -> entryMethod.getParameterTypes().add( p.getName() ) );
        return entryMethod;
    }

    public Entry addMethods(final Entry entry, final Class<?> c) {
        fun.apply(c).forEach( m -> entry.getMethods().add( this.newMethod(m.getName(), m.getParameterTypes())));
        return entry;
    }

    public Entry addConstructors(final Entry entry, final Class<?> c) {
        Arrays.asList(c.getConstructors()).forEach( m -> entry.getMethods().add( this.newMethod(EntryHelper.INIT_METHOD_NAME, m.getParameterTypes())));
        return entry;
    }

    public Entry toEntry(final Class<?> c) {
        return this.addMethods( new Entry( c.getName() ), c );
    }

    public Entry toEntry(final Class<?> c, final boolean constructors) {
        if ( constructors ) {
            return this.toEntryWithConstructors( c );
        } else {
            return this.toEntry( c );
        }
    }

    public Entry toEntryWithConstructors(final Class<?> c) {
        return this.addConstructors( this.addMethods( new Entry( c.getName() ), c ), c );
    }

    public static final ReflectConfigUtil ALL_METHODS = new ReflectConfigUtil();

    public static final ReflectConfigUtil DECLARED_METHODS = new ReflectConfigUtil( c -> Arrays.asList( c.getDeclaredMethods() ) );

    public static final ReflectConfigUtil GETTERS_ONLY = new ReflectConfigUtil( c -> Arrays.asList( c.getMethods() ).stream().filter( m -> m.getName().startsWith( "get" ) ).collect( Collectors.toList() ) );

    public static final ReflectConfigUtil SETTERS_ONLY = new ReflectConfigUtil( c -> Arrays.asList( c.getMethods() ).stream().filter( m -> m.getName().startsWith( "set" ) ).collect( Collectors.toList() ) );

    public static final ReflectConfigUtil GETTERS_SETTERS = new ReflectConfigUtil( c -> Arrays.asList( c.getMethods() ).stream().filter(
            m -> m.getName().startsWith( "get" ) || m.getName().startsWith( "set" ) ).collect( Collectors.toList() ) );


}
