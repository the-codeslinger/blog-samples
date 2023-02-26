package com.example.demo.producer.dto;

public record RequestMessage(String type, String referrer, RequestMessagePayload payload) {
}
