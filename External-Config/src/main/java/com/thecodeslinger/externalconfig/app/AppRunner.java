package com.thecodeslinger.externalconfig.app;

import com.thecodeslinger.externalconfig.config.ExternalConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {

    private final ExternalConfigProperties externalConfigProperties;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("-> AppRunner.run() Command Line Arguments");

        for (final var arg : args) {
            System.out.println("Argument: " + arg);
        }

        System.out.println("-> ExternalConfigProperties");

        System.out.println("Input path: " + externalConfigProperties.getInput().getPath());
        System.out.println("Input path: " + externalConfigProperties.getOutput().getPath());
    }
}
