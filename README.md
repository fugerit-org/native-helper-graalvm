# Native Helper for Graal VM

Utilities for Graal VM metadata generation.

[![Keep a Changelog v1.1.0 badge](https://img.shields.io/badge/changelog-Keep%20a%20Changelog%20v1.1.0-%23E05735)](https://github.com/fugerit-org/native-helper-graalvm/blob/main/CHANGELOG.md)
[![license](https://img.shields.io/badge/License-Apache%20License%202.0-teal.svg)](https://opensource.org/licenses/Apache-2.0)  
[![code of conduct](https://img.shields.io/badge/Conduct-Contributor%20Covenant%202.1-purple.svg)](https://github.com/fugerit-org/fj-universe/blob/main/CODE_OF_CONDUCT.md)
[![Maven Central](https://img.shields.io/maven-central/v/org.fugerit.java/native-helper-graalvm.svg)](https://central.sonatype.com/artifact/org.fugerit.java/native-helper-graalvm)  
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fugerit-org_native-helper-graalvm&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fugerit-org_native-helper-graalvm)  
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fugerit-org_native-helper-graalvm&metric=coverage)](https://sonarcloud.io/summary/new_code?id=fugerit-org_native-helper-graalvm)

[![Java runtime version](https://img.shields.io/badge/run%20on-java%208+-%23113366.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/java11.html)
[![Java build version](https://img.shields.io/badge/build%20on-java%2011+-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/java11.html)
[![Apache Maven](https://img.shields.io/badge/Apache%20Maven-3.9.0+-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)](https://universe.fugerit.org/src/docs/versions/maven3_9.html)
[![Fugerit Github Project Conventions](https://img.shields.io/badge/Fugerit%20Org-Project%20Conventions-1A36C7?style=for-the-badge&logo=Onlinect%20Playground&logoColor=white)](https://universe.fugerit.org/src/docs/conventions/index.html)

This library allows generation of [graalvm](https://www.graalvm.org/) metadata.

There is also a [native-helper-maven-plugin](https://github.com/fugerit-org/native-helper-maven-plugin) 
available, to add the generation to project build.

## 1. Quickstart

1.1 Add dependency 

```xml
<dependency>
    <groupId>org.fugerit.java</groupId>
    <artifactId>native-helper-graalvm</artifactId>
    <version>x.y.z</version>
</dependency>
```

1.2 Create your reflect config entry model

```
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
```

1.3 Output will be

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
}, {
  "name" : "org.fugerit.java.nhg.reflect.config.EntryMethod",
  "methods" : [ {
    "name" : "<init>",
    "parameterTypes" : [ ]
  }, {
    "name" : "getClass",
    "parameterTypes" : [ ]
  }, {
    "name" : "getName",
    "parameterTypes" : [ ]
  }, {
    "name" : "getParameterTypes",
    "parameterTypes" : [ ]
  } ]
} ]
```

## 2. Auto generate reflect config entries

It is possible to use *ReflectConfigUtil* to generate reflect config entries content.

```java
// choose ReflectConfigUtil or define a cusom one
// in this example we will render only getters methods
ReflectConfigUtil reflectConfigUtil = ReflectConfigUtil.GETTERS_ONLY;
// creates new reflect config entry, with selected methods and all constructors
Entry entry = reflectConfigUtil.toEntryWithConstructors( c );
// order methods, constructors first, then methods in alphabetical order
EntryHelper.fixedOrder( entry );
```

Here is a full [code example](src/test/java/test/org/fugerit/java/nhg/TestSampleReflectConfigUtil.java).

## 3. Generate reflect config entries with NativeHelperFacade 

To bulk generate reflect-config.json, you can use the *NativeHelperFacade*.

### 3.1 Write native-helper-config.yaml

*NativeHelperFacade* uses a yaml configuration file to generate the entries.

Here is a sample [native-helper-config.yaml](src/main/config/native-helper-config.yaml) 
configuration file :

```yaml
#Sample native helper config file
---
generate:
- className: org.fugerit.java.nhg.reflect.config.Entry
  skipConstructors: true
  mode: getters
- className: org.fugerit.java.nhg.reflect.config.EntryCondition
  skipConstructors: true
  mode: getters
- className: org.fugerit.java.nhg.reflect.config.EntryField
  skipConstructors: true
  mode: getters
- className: org.fugerit.java.nhg.reflect.config.EntryMethod
  skipConstructors: true
  mode: getters
```

### 3.2 Generate reflect config entries

And then use *NativeHelperFacade* to generate entries :

```java
// path the native-helper-config.yaml file
String path = "src/test/resources/tool/config/native-helper-config-1.yaml";
// reads configuration
NativeHelperConfig config = NativeHelperFacade.loadConfig( path );
// creates entries from configuration
List<Entry> entries = NativeHelperFacade.generateEntries( config );
```

Here is a full [code example](src/test/java/test/org/fugerit/java/nhg/TestSampleNativeHelperFacade.java).

### 3.3 native-helper-config.yaml reference


top level properties :

| name                        | default | required | type                  | description                                                            |
|-----------------------------|---------|----------|-----------------------|------------------------------------------------------------------------|
| reflectConfigJsonOutputPath | *none*  | *false*  | *string*              | default generation path for *reflect-config.json* file                 |
| createParentDirectory       | *false* | *false*  | *boolean*             | if *true* will generate *reflectConfigJsonOutputPath* parent directory |
| jarPackageDiscovery         | *false* | *false*  | *boolean*             | if *true* will try to discover packages in jar                         |
| generate                    | *none*  | *false*  | list of entry config  | list of entries to generate                                            |
| merge                       | *none*  | *false*  | list of merge coinfig | list of reflect-config.json to merge in the output                     |



entry config properties : 

| name                | default | required | type      | description                                                                     |
|---------------------|---------|----------|-----------|---------------------------------------------------------------------------------|
| className           | *none*  | *false*  | *string*  | fully qualified class name (alternative to *packageName*)                       |
| packageName         | *none*  | *false*  | *string*  | fully qualified package name (alternative to *className*) (*)                   |
| excludeClassNames   | *none*  | *false*  | *string*  | if *packageName* is set, comma separeted list of class simple names to excludes |
| mode                | *none*  | *true*   | *string*  | accept values : *getters*, *setters*, *getters_setters*, *all*                  |
| skipGenerators      | *false* | *false*  | *boolean* | if set to *true* will not generate constructors metadata                        |
| typeReachable       | *none*  | *false*  | *string*  | type for typeReachable condition                                                |
| jarPackageDiscovery | *false* | *false*  | *boolean* | if *true* will try to discover packages in jar                                  |

(*) currently package packed ar only supported if *jarPackageDiscovery* is set to *true*

merge config properties :

| name              | default       | required | type      | description                                                                                                          |
|-------------------|---------------|----------|-----------|----------------------------------------------------------------------------------------------------------------------|
| reflectConfigPath | *none*        | *true*   | *string*  | path to reflect-config.json file to merge                                                                            |
| mode              | *warnOnError* | *false*  | *string*  | Can be *warnOnError* : error will be just logged on error, *failOnError* : the generation will fail in case of error |

## 4. Generate reflect-config.json file

Once the entry list has been generated, use the *GenerateReflectConfig* utility to write the json file.

```java
File reflectConfigFile = new File( "META-INF/native-image/relfect-config.json" ):
GenerateReflectConfig generateReflectConfig = new GenerateReflectConfig();
try (Writer writer = new FileWriter( reflectConfigFile ) ) {
    generateReflectConfig.generate( writer, entries );
}
```
