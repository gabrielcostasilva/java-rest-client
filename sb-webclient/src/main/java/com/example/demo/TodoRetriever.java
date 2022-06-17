package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TodoRetriever {

    private final String baseUrl = "https://jsonplaceholder.typicode.com/todos";

	public Flux<Todo> readAll() {

		return WebClient
                    .builder()
                    .baseUrl(this.baseUrl)
                    .build()
                    .get()
                    .retrieve()
                    .bodyToFlux(Todo.class);
	}

	public Mono<Todo> readSingle(int id) {

		return WebClient
                .builder()
                .baseUrl(this.baseUrl)
                .build()
                .get()
                .uri("/" + id)
                .retrieve()
                .bodyToMono(Todo.class);
	}

    public Mono<Todo> create(Todo todo) {
        
        return WebClient
                    .builder()
                    .baseUrl(baseUrl)
                    .build()
                    .post()
                    .body(BodyInserters.fromValue(todo))
                    .retrieve()
                    .bodyToMono(Todo.class);
    }

    public Mono<ResponseEntity<Todo>> delete(int id) {
        
        return WebClient
                    .builder()
                    .baseUrl(baseUrl)
                    .build()
                    .delete()
                    .uri("/" + id)
                    .retrieve()
                    .toEntity(Todo.class);
                    
    }

    public Mono<Todo> update(Todo todo) {
        
        return WebClient
                    .builder()
                    .baseUrl(baseUrl)
                    .build()
                    .put()
                    .uri("/" + todo.id())
                    .body(BodyInserters.fromValue(todo))
                    .retrieve()
                    .bodyToMono(Todo.class);
    }
}
