
package org.fugerit.java.nhg.reflect.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "typeReachable"
})
@AllArgsConstructor
@NoArgsConstructor
public class EntryCondition {

    /**
     * Fully qualified class name of the class that must be reachable in order to register the class <name> for reflection
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("typeReachable")
    @Getter @Setter private String typeReachable;

}
