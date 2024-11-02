package org.fugerit.java.nhg;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonHelper {

    private JacksonHelper() {}

    public static ObjectMapper newObjectMapper() {
        return new ObjectMapper();
    }

}
