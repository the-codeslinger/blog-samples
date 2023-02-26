package com.example.demo.api;

import com.example.demo.service.RequestMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FakeNewsController {

	private final RequestMessageProducer requestMessageProducer;

	@PostMapping(path = "/fake-news")
	public void postMany(@RequestParam("alternativeFact") String alternativeFact) {
		requestMessageProducer.sendMessage(alternativeFact);
	}
}
