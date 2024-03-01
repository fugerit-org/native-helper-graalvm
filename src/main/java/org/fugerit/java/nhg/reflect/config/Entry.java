package org.fugerit.java.nhg.reflect.config;

import lombok.*;

import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
public class Entry {

    @Getter @Setter
    private EntryCondition condition;

    @NonNull @Getter @Setter
    private String name;

    @Getter @Setter
    private List<EntryField> fields;

    @Getter @Setter
    private List<EntryMethod> methods;

}
