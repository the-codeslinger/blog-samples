package org.thecodeslinger.springcloudstreamskafka.service;

import lombok.extern.slf4j.Slf4j;
import org.thecodeslinger.springcloudstreamskafka.consumer.dto.RequestMessage;

/**
 * Sample service for demonstration purposes of how to use a service in a consumer
 * function.
 */
@Slf4j
public class MessageAggregationService {

    public void evaluateMessage(RequestMessage message) {
        log.info("Fake-news received: '{}'.", message.payload().alternativeFact());
    }
}
