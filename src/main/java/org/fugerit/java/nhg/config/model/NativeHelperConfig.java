package org.fugerit.java.nhg.config.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@ToString
public class NativeHelperConfig {

    @Getter @Setter
    private String reflectConfigJsonOutputPath;

    @Getter @Setter
    private boolean createParentDirectory;

    @Getter @Setter
    private List<GenerateConfig> generate;

}
