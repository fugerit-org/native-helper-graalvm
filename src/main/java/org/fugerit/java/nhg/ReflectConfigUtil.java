package org.fugerit.java.nhg;

import org.fugerit.java.nhg.reflect.config.Entry;
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

    public Entry toEntry(Class<?> c) {
        final Entry entry = new Entry( c.getName() );
        entry.setMethods(
                fun.apply(c).stream().map( m -> {
                    final EntryMethod entryMethod = new EntryMethod( m.getName() );
                    Arrays.stream( m.getParameterTypes() ).forEach( p -> entryMethod.getParameterTypes().add( p.getName() ) );
                    return entryMethod;
                } ).collect( Collectors.toList() ) );
        return entry;
    }

    public static final ReflectConfigUtil ALL_METHODS = new ReflectConfigUtil();

    public static final ReflectConfigUtil DECLARED_METHODS = new ReflectConfigUtil( c -> Arrays.asList( c.getDeclaredMethods() ) );

    public static final ReflectConfigUtil GETTERS_ONLY = new ReflectConfigUtil( c -> Arrays.asList( c.getMethods() ).stream().filter( m -> m.getName().startsWith( "get" ) ).collect( Collectors.toList() ) );

    public static final ReflectConfigUtil SETTERS_ONLY = new ReflectConfigUtil( c -> Arrays.asList( c.getMethods() ).stream().filter( m -> m.getName().startsWith( "set" ) ).collect( Collectors.toList() ) );

    public static final ReflectConfigUtil GETTERS_SETTERS = new ReflectConfigUtil( c -> Arrays.asList( c.getMethods() ).stream().filter(
            m -> m.getName().startsWith( "get" ) || m.getName().startsWith( "set" ) ).collect( Collectors.toList() ) );


}
