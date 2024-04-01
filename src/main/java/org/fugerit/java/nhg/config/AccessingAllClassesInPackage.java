package org.fugerit.java.nhg.config;


import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.lang.helpers.ClassHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class AccessingAllClassesInPackage {

    private AccessingAllClassesInPackage() {}

    public static Set<Class<?>> findAllClassesUsingClassLoader(String packageName) throws ConfigRuntimeException {
        return SafeFunction.get( () -> {
            InputStream stream = ClassHelper.getDefaultClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            return reader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(line -> getClass(line, packageName))
                    .collect(Collectors.toSet());
        } );
    }

    private static Class<?> getClass(String className, String packageName) {
        return SafeFunction.get( () -> {
            log.debug( "packageName : {}, className : {}", packageName, className );
            return Class.forName(packageName+"."+className.substring(0, className.lastIndexOf('.')));
        } );
    }

}