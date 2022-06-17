package com.example.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class FeignApplication {

	private final TodoRetriever todoRetriever;

	public static void main(String[] args) {
		SpringApplication.run(FeignApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() {

		// System.out.println(todoRetriever.readAll());
		// System.out.println(todoRetriever.readSingle(1));
		// System.out.println(todoRetriever.create(new Todo(1, 1, "Something", true)));
		// System.out.println(todoRetriever.delete(1));
		// System.out.println(todoRetriever.update(1, new Todo(1, 1, "Something", true)));
	}

}
