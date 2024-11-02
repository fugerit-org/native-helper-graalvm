
package org.fugerit.java.nhg.reflect.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "parameterTypes"
})
@RequiredArgsConstructor
@NoArgsConstructor
public class EntryQueriedMethod {

    /*
     * Method name that are queried for this class
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    @NonNull @Getter @Setter
    private String name;
    /*
     * List of methods to register for this class that are only looked up but not invoked.
     * <p>
     * 
     * 
     */
    @JsonProperty("parameterTypes")
    @Getter @Setter
    private List<String> parameterTypes = new ArrayList<>();

}
