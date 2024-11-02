
package org.fugerit.java.nhg.reflect.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


/*
 * List of methods from this class that are registered for reflection
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "parameterTypes"
})
@RequiredArgsConstructor
@NoArgsConstructor
public class EntryMethod {

    /*
     * Method name that should be registered for this class
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    @NonNull @Getter @Setter
    private String name;

    @JsonProperty("parameterTypes")
    @Getter @Setter
    private List<String> parameterTypes = new ArrayList<>();

}
