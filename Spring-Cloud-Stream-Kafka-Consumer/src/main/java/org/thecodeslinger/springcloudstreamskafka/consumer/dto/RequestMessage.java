package com.thecodeslinger.springcloudstreamskafka.consumer.dto;

public record RequestMessage(String type, String referrer, RequestMessagePayload payload) {
}
