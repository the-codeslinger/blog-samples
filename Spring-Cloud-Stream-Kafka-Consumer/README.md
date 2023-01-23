Spring Cloud Stream Kafka Consumer
==================================

This is a sample application using Spring Cloud Stream together with the Kafka 
message broker to implement a message consumer.

[Quickstart guide](https://kafka.apache.org/quickstart) to get Apache Kafka 
running on your machine. Requires Linux or macOS (WSL on Windows).

Run the application with `mvnw spring-boot:run`

*Correctly formatted input message*

Paste into the `kafka-console-producer.sh`.
```json
{"type": "fake-news", "referrer": "anonymous", "payload": {"alternativeFact": "Episode One is the best Star Wars movie"}}
```

Application output:
```shell
2023-01-23T13:39:07.352+01:00  INFO 90892 --- [container-0-C-1] o.t.s.service.MessageAggregationService : Fake-news received: 'Episode One is the best Star Wars movie'.
```

*Incorrectly formatted input message*

More like: not formatted at all.

```text
Episode One is the best Star Wars movie
```

Application output:
```shell
2023-01-23T13:38:49.569+01:00 ERROR 90892 --- [container-0-C-1] o.t.s.config.ConsumerConfig : Fake news revealed: 'Episode One is the best Star Wars movie'.
```

**Gotchas**

The following error message can be received when handling the input message 
results in an error and no error handler is defined for the binding.
```shell
Caused by: org.springframework.messaging.MessageDeliveryException: Dispatcher has no subscribers for channel 'kafka-127127710.requestMessageConsumer-in-0.errors'.
```

Solution:

* https://github.com/spring-cloud/spring-cloud-stream/issues/2610#issuecomment-1371971459
* https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/spring-cloud-stream.html#_handle_error_messages
