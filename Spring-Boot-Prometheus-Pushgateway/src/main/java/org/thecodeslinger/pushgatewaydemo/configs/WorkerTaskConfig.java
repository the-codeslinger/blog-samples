package com.thecodeslinger.pushgatewaydemo.configs;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.thecodeslinger.pushgatewaydemo.tasks.WorkerTask;

@Configuration
@RequiredArgsConstructor
public class WorkerTaskConfig {

    private final MeterRegistry meterRegistry;

    @Bean
    public WorkerTask workerTask() {
        return new WorkerTask(meterRegistry);
    }
}
