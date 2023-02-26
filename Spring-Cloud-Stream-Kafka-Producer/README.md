Spring Cloud Stream Kafka Producer
==================================

This is a sample application using Spring Cloud Stream together with the Kafka
message broker to implement a message producer.

This sample can be used together with the [consumer sample](../Spring-Cloud-Stream-Kafka-Consumer).

[Quickstart guide](https://kafka.apache.org/quickstart) to get Apache Kafka
running on your machine. Requires Linux or macOS (WSL on Windows).

Run the application with `mvnw spring-boot:run`

*Correctly formatted input message*

Send a message to the application's controller to trigger producing to Kafka.
```shell
curl -i -X POST "http://localhost:8081/fake-news?alternativeFact=Episode%20One%20is%20the%20best%20Star%20Wars%20movie"
```

Application output:
```shell
02-26T07:59:43.466+01:00  INFO 6401 --- [nio-8081-exec-1] c.e.demo.service.RequestMessageProducer  : Manually sending message GenericMessage [payload=RequestMessage[type=lie, referrer=Liar, payload=RequestMessagePayload[alternativeFact=Episode One is the best Star Wars movie]], headers={id=9e41ccaf-3ce7-59f3-7865-fb365ecbf5d3, timestamp=1677394783466}]
```

When the consumer is running, it will output this message.
```shell
2023-02-26T07:59:43.551+01:00  INFO 6406 --- [container-0-C-1] o.t.s.service.MessageAggregationService  : Fake-news received: 'Episode One is the best Star Wars movie'.
```