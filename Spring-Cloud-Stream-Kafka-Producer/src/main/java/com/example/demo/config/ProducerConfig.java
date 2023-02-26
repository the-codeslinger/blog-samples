package com.example.demo.config;


import com.example.demo.producer.dto.RequestMessage;
import com.example.demo.producer.RequestMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Slf4j
@Configuration
public class ProducerConfig {

    /**
     * Intermediary object that sits between the {@link Supplier} used by Spring Cloud
     * Stream and the application code. The application code uses this sink as the "output
     * stream" for writing the messages.
     * <p>
     * Optionally, {@link StreamBridge} is an alternative to replace the sink and
     * supplier.
     *
     * @see https://github.com/reactor/reactor-core/blob/main/docs/asciidoc/processors.adoc
     * @see https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/spring-cloud-stream.html#_sending_arbitrary_data_to_an_output_e_g_foreign_event_driven_sources
     */
	@Bean
    public Sinks.Many<Message<RequestMessage>> messageSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    /**
     * Defines the bean that is used as the message producer.
     *
     * Used in application.yml {@code spring.cloud.stream.function.definition}.
     */
    @Bean
    public RequestMessageProducer requestMessageProducer() {
        return new RequestMessageProducer(messageSink());
    }
}
