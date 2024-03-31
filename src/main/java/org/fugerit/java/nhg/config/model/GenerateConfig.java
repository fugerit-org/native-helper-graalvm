package org.fugerit.java.nhg.config.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class GenerateConfig {

    @Getter @Setter
    private String className;

    @Getter @Setter
    private String mode;

    @Getter @Setter
    private boolean skipConstructors;

    @Getter @Setter
    private String typeReachable;

}
