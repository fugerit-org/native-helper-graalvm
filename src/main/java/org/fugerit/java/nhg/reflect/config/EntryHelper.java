package org.fugerit.java.nhg.reflect.config;

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

}
