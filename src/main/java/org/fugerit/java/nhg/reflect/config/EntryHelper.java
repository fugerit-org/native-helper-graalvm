package org.fugerit.java.nhg.reflect.config;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EntryHelper {

    private EntryHelper() {}

    public static final String INIT_METHOD_NAME = "<init>";

    public static Entry addDefaultInit( Entry entry ) {
        entry.getMethods().add( new EntryMethod( INIT_METHOD_NAME ) );
        return entry;
    }

    public static Entry addInit(Entry entry, List<String> parametersTypes ) {
        EntryMethod initMethod = new EntryMethod( INIT_METHOD_NAME );
        initMethod.setParameterTypes( parametersTypes );
        entry.getMethods().add( initMethod );
        return entry;
    }

    public static Entry fixedOrder( Entry entry ) {
        Collections.sort( entry.getMethods(),
                (o1, o2) -> (o1.getName()+o1.getParameterTypes().size()).compareTo( o2.getName()+o2.getParameterTypes().size() ) );
        return entry;
    }

}
