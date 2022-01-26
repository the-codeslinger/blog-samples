package com.thecodeslinger.externalconfig.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("external-config")
public class ExternalConfigProperties {

    private Location input;
    private Location output;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {
        private String path;
    }
}
