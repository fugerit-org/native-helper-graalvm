package org.fugerit.java.nhg.reflect.config;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
public class EntryMethod {

    @NonNull @Getter @Setter
    private String name;

    @Getter
    @Setter
    private List<String> parameterTypes = new ArrayList<>();

}
