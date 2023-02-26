package com.example.demo.producer;

import com.example.demo.producer.dto.RequestMessage;
import com.example.demo.producer.dto.RequestMessagePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
public class RequestMessageProducer implements Supplier<Flux<Message<RequestMessage>>> {

	private final Sinks.Many<Message<RequestMessage>> messageSink;

	/**
	 * The service method for clients to trigger sending data to the Kafka topic. Here all
	 * the business logic or validation can take place before pushing the message to the
	 * broker.
	 */
	public void sendMessage(String alternativeFact) {

		var payload = new RequestMessagePayload(alternativeFact);
		var message = new RequestMessage("lie", "Liar", payload);

		messageSink.emitNext(new GenericMessage<>(message), Sinks.EmitFailureHandler.FAIL_FAST);
	}

	/**
	 * Do not use in client code. This method implements the {@link Supplier} interface
	 * used by Spring Cloud Stream to produce messages to the broker.
	 */
	@Override
	public Flux<Message<RequestMessage>> get() {
		return messageSink.asFlux()
				.doOnNext(m -> log.info("Manually sending message {}", m))
				.doOnError(t -> log.error("Error encountered", t));
	}
}
