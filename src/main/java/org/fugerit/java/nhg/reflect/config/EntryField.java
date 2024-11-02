
package org.fugerit.java.nhg.reflect.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name"
})
@RequiredArgsConstructor
@NoArgsConstructor
public class EntryField {

    /**
     * Name of the field that should be registered for reflection
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    @NonNull @Getter @Setter
    private String name;

}
