package org.fugerit.java.nhg.config.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class MergeConfig {

    @Getter @Setter
    private String reflectConfigPath;

    @Getter @Setter
    private String mode;

}
