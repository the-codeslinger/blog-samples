package com.thecodeslinger.externalconfig.config;

import com.thecodeslinger.externalconfig.app.AppRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ExternalConfigProperties.class)
public class AppConfig {

    private final ExternalConfigProperties externalConfigProperties;

    @Bean
    public AppRunner appRunner() {
        return new AppRunner(externalConfigProperties);
    }
}
