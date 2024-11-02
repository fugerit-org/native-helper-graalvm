
package org.fugerit.java.nhg.reflect.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// from this class we want to exclude the empty lists
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({
    "condition",
    "name",
    "methods",
    "queriedMethods",
    "fields",
    "allDeclaredClasses",
    "allDeclaredMethods",
    "allDeclaredFields",
    "allDeclaredConstructors",
    "allPublicClasses",
    "allPublicMethods",
    "allPublicFields",
    "allPublicConstructors",
    "allRecordComponents",
    "allPermittedSubclasses",
    "allNestMembers",
    "allSigners",
    "queryAllDeclaredMethods",
    "queryAllDeclaredConstructors",
    "queryAllPublicMethods",
    "queryAllPublicConstructors",
    "unsafeAllocated"
})
@RequiredArgsConstructor
@NoArgsConstructor
/**
 * Generated using : https://www.jsonschema2pojo.org/
 * From json schema definition :
 * https://docs.oracle.com/en/graalvm/jdk/21/docs/docs/reference-manual/native-image/assets/reflect-config-schema-v1.0.0.json
 * As said in :
 * https://docs.oracle.com/en/graalvm/jdk/21/docs/reference-manual/native-image/metadata/#specifying-reflection-metadata-in-json
 */
public class Entry {

    @JsonProperty("condition")
    @Getter @Setter private EntryCondition condition;
    @JsonProperty("name")
    @NonNull @Getter @Setter private String name;

    @JsonProperty("methods")
    @Getter @Setter private List<EntryMethod> methods = new ArrayList<>();;
    /*
     * List of methods that are queried for the class declared in <name>
     * <p>
     *
     *
     */
    @JsonProperty("queriedMethods")
    @Getter @Setter private List<EntryQueriedMethod> queriedMethods = new ArrayList<>();;
    /*
     * List of class fields that can be looked up, read, or modified for the class declared in <name>
     * <p>
     *
     *
     */
    @JsonProperty("fields")
    @Getter @Setter private List<EntryField> fields = new ArrayList<>();;
    /*
     * Register classes which would be returned by the java.lang.Class#getDeclaredClasses call
     * <p>
     *
     *
     */
    @JsonProperty("allDeclaredClasses")
    @Getter @Setter private Boolean allDeclaredClasses;
    /*
     * Register methods which would be returned by the java.lang.Class#getDeclaredMethods call
     * <p>
     *
     *
     */
    @JsonProperty("allDeclaredMethods")
    @Getter @Setter private Boolean allDeclaredMethods;
    /*
     * Register fields which would be returned by the java.lang.Class#getDeclaredFields call
     * <p>
     *
     *
     */
    @JsonProperty("allDeclaredFields")
    @Getter @Setter private Boolean allDeclaredFields;
    /*
     * Register constructors which would be returned by the java.lang.Class#getDeclaredConstructors call
     * <p>
     * 
     * 
     */
    @JsonProperty("allDeclaredConstructors")
    @Getter @Setter private Boolean allDeclaredConstructors;
    /*
     * Register all @Getter @Setter private classes which would be returned by the java.lang.Class#getClasses call
     * <p>
     * 
     * 
     */
    @JsonProperty("allPublicClasses")
    @Getter @Setter private Boolean allPublicClasses;
    /*
     * Register all @Getter @Setter private methods which would be returned by the java.lang.Class#getMethods call
     * <p>
     * 
     * 
     */
    @JsonProperty("allPublicMethods")
    @Getter @Setter private Boolean allPublicMethods;
    /*
     * Register all @Getter @Setter private fields which would be returned by the java.lang.Class#getFields call
     * <p>
     * 
     * 
     */
    @JsonProperty("allPublicFields")
    @Getter @Setter private Boolean allPublicFields;
    /*
     * Register all @Getter @Setter private constructors which would be returned by the java.lang.Class#getConstructors call
     * <p>
     * 
     * 
     */
    @JsonProperty("allPublicConstructors")
    @Getter @Setter private Boolean allPublicConstructors;
    /*
     * Register record components which would be returned by the java.lang.Class#getRecordComponents call
     * <p>
     * 
     * 
     */
    @JsonProperty("allRecordComponents")
    @Getter @Setter private Boolean allRecordComponents;
    /*
     * Register permitted subclasses which would be returned by the java.lang.Class#getPermittedSubclasses call
     * <p>
     * 
     * 
     */
    @JsonProperty("allPermittedSubclasses")
    @Getter @Setter private Boolean allPermittedSubclasses;
    /*
     * Register nest members which would be returned by the java.lang.Class#getNestMembers call
     * <p>
     * 
     * 
     */
    @JsonProperty("allNestMembers")
    @Getter @Setter private Boolean allNestMembers;
    /*
     * Register signers which would be returned by the java.lang.Class#getSigners call
     * <p>
     * 
     * 
     */
    @JsonProperty("allSigners")
    @Getter @Setter private Boolean allSigners;
    /*
     * Register methods which would be returned by the java.lang.Class#getDeclaredMethods call but only for lookup
     * <p>
     * 
     * 
     */
    @JsonProperty("queryAllDeclaredMethods")
    @Getter @Setter private Boolean queryAllDeclaredMethods;
    /*
     * Register constructors which would be returned by the java.lang.Class#getDeclaredConstructors call but only for lookup
     * <p>
     * 
     * 
     */
    @JsonProperty("queryAllDeclaredConstructors")
    @Getter @Setter private Boolean queryAllDeclaredConstructors;
    /*
     * Register all @Getter @Setter private methods which would be returned by the java.lang.Class#getMethods call but only for lookup
     * <p>
     * 
     * 
     */
    @JsonProperty("queryAllPublicMethods")
    @Getter @Setter private Boolean queryAllPublicMethods;
    /*
     * Register all @Getter @Setter private constructors which would be returned by the java.lang.Class#getConstructors call but only for lookup
     * <p>
     * 
     * 
     */
    @JsonProperty("queryAllPublicConstructors")
    @Getter @Setter private Boolean queryAllPublicConstructors;
    /*
     * Allow objects of this class to be instantiated with a call to jdk.internal.misc.Unsafe#allocateInstance
     * <p>
     * 
     * 
     */
    @JsonProperty("unsafeAllocated")
    @Getter @Setter private Boolean unsafeAllocated;

}
