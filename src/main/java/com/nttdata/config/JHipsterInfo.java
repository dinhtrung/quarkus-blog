package com.nttdata.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;


@ConfigMapping(prefix = "jhipster.info")
public interface JHipsterInfo {

    @WithName("swagger.enable")
    Boolean isEnable();

}
