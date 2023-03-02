package com.thecodeslinger.pushgatewaydemo.tasks;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.util.Random;

@Slf4j
public class WorkerTask implements CommandLineRunner {

    private final Timer runtimer;

    public WorkerTask(MeterRegistry meterRegistry) {
        runtimer = meterRegistry.timer("workertask.runtime");
    }

    @Override
    public void run(String... args) {

        var random = new Random();

        // Simulate a number of tasks of varying duration.
        // Each simulated task is timed.
        for (var c = 0; c < random.nextInt(10); c++) {
            log.info("Execute task #{}.", c);
            runtimer.record(() -> {
                try {
                    var duration = random.nextInt(1000);
                    Thread.sleep(duration);
                    log.info("Task took {}ms to execute.", duration);
                } catch (InterruptedException e) {
                    log.warn("Somebody interrupted my sleep. {}", e.getMessage());
                }
            });
        }
    }
}
