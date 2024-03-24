# Native Helper for Graal VM

Utilities for Graal VM code generation

[![Keep a Changelog v1.1.0 badge](https://img.shields.io/badge/changelog-Keep%20a%20Changelog%20v1.1.0-%23E05735)](https://github.com/fugerit-org/native-helper-graalvm/blob/main/CHANGELOG.md)
[![license](https://img.shields.io/badge/License-Apache%20License%202.0-teal.svg)](https://opensource.org/licenses/Apache-2.0)  
[![code of conduct](https://img.shields.io/badge/Conduct-Contributor%20Covenant%202.1-purple.svg)](https://github.com/fugerit-org/fj-universe/blob/main/CODE_OF_CONDUCT.md)
[![Maven Central](https://img.shields.io/maven-central/v/org.fugerit.java/native-helper-graalvm.svg)](https://mvnrepository.com/artifact/org.fugerit.java/native-helper-graalvm)  
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fugerit-org_native-helper-graalvm&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fugerit-org_native-helper-graalvm)  
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fugerit-org_native-helper-graalvm&metric=coverage)](https://sonarcloud.io/summary/new_code?id=fugerit-org_native-helper-graalvm)

[![Java runtime version](https://img.shields.io/badge/run%20on-java%208+-%23113366.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/java11.html)
[![Java build version](https://img.shields.io/badge/build%20on-java%2011+-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/java11.html)
[![Apache Maven](https://img.shields.io/badge/Apache%20Maven-3.9.0+-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)](https://universe.fugerit.org/src/docs/versions/maven3_9.html)
[![Fugerit Github Project Conventions](https://img.shields.io/badge/Fugerit%20Org-Project%20Conventions-1A36C7?style=for-the-badge&logo=Onlinect%20Playground&logoColor=white)](https://universe.fugerit.org/src/docs/conventions/index.html)

## Quickstart

1. Add dependency 

```xml
<dependency>
    <groupId>org.fugerit.java</groupId>
    <artifactId>native-helper-graalvm</artifactId>
    <version>x.y.z</version>
</dependency>
```

2. Create your model

```java
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
```
3. Output will be

```json
[ {
  "condition" : {
    "typeReachable" : "java.lang.String"
  },
  "name" : "java.lang.String",
  "fields" : [ {
    "name" : "prop1"
  } ],
  "methods" : [ {
    "name" : "test1",
    "parameterTypes" : [ ]
  } ]
}, {
  "condition" : {
    "typeReachable" : "java.lang.Double"
  },
  "name" : "java.lang.Integer",
  "fields" : [ {
    "name" : "prop2"
  } ],
  "methods" : [ {
    "name" : "test2",
    "parameterTypes" : [ "java.util.Date" ]
  } ]
} ]
```