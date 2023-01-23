package org.thecodeslinger.springcloudstreamskafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.thecodeslinger.springcloudstreamskafka.consumer.dto.RequestMessage;
import org.thecodeslinger.springcloudstreamskafka.service.MessageAggregationService;

import java.util.function.Consumer;

/**
 * Example class to demonstrate that consumer or producer beans must not be defined
 * inline in Java config classes.
 */
@Slf4j
@RequiredArgsConstructor
public class RequestMessageConsumer implements Consumer<Message<RequestMessage>> {

    private final MessageAggregationService messageAggregationService;

    @Override
    public void accept(Message<RequestMessage> requestMessageMessage) {
        messageAggregationService.evaluateMessage(requestMessageMessage.getPayload());
    }
}
