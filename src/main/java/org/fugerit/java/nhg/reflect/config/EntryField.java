package org.fugerit.java.nhg.reflect.config;

import lombok.*;

@RequiredArgsConstructor
@NoArgsConstructor
public class EntryField {

    @NonNull @Getter @Setter
    private String name;

}
