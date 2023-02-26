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
Manually sending message GenericMessage [payload=RequestMessage[type=lie, referrer=Liar, payload=RequestMessagePayload[alternativeFact=Party hard]], headers={id=401505d0-6904-966e-3cb3-feef3a4a3c51, timestamp=1677394031888}]
```
