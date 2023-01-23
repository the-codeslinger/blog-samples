package org.thecodeslinger.springcloudstreamskafka.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.ErrorMessage;
import org.thecodeslinger.springcloudstreamskafka.consumer.RequestMessageConsumer;
import org.thecodeslinger.springcloudstreamskafka.service.MessageAggregationService;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

@Slf4j
@Configuration
public class ConsumerConfig {

    @Bean
    public MessageAggregationService messageAggregationService() {
        return new MessageAggregationService();
    }

    /**
     * Defines the bean that is used as the message consumer.
     *
     * Used in application.yml {@code spring.cloud.stream.function.definition}.
     */
    @Bean
    public RequestMessageConsumer requestMessageConsumer() {
        return new RequestMessageConsumer(messageAggregationService());
    }

    /**
     * Defines the bean that is used when an error occurs in or before the
     * {@link RequestMessageConsumer}.
     * <p>
     * Used in:
     * <ul>
     *     <li>application.yml {@code spring.cloud.stream.function.definition}.</li>
     *     <li>application.yml {@code spring.cloud.stream.bindings.requestMessageConsumer-in-0.error-handler-definition}.</li>
     * </ul>
     */
    @Bean
    public Consumer<ErrorMessage> fakeNewsError() {
        return errorMessage -> {
            var orig = errorMessage.getOriginalMessage();
            var falseData = (byte[])orig.getPayload();
            log.error("Fake news revealed: '{}'.", new String(falseData, StandardCharsets.UTF_8));
        };
    }
}
