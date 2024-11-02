package org.fugerit.java.nhg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JacksonHelper {

    private JacksonHelper() {}

    public static ObjectMapper newObjectMapper() {
        return new ObjectMapper();
    }

}
