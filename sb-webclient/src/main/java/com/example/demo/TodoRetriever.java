package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TodoRetriever {

    private final String baseUrl = "https://jsonplaceholder.typicode.com/todos";

	public Flux<Todo> readAllTodos() {

		return WebClient
                    .builder()
                    .baseUrl(this.baseUrl)
                    .build()
                    .get()
                    .uri("/")
                    .retrieve()
                    .bodyToFlux(Todo.class);
	}

	public Mono<Todo> readSingleTodo() {

		return WebClient
                .builder()
                .baseUrl(this.baseUrl)
                .build()
                .get()
                .uri("/1")
                .retrieve()
                .bodyToMono(Todo.class);
	}

    public Mono<Todo> createTodo(Todo todo) {
        
        return WebClient
                    .builder()
                    .baseUrl(baseUrl)
                    .build()
                    .post()
                    .uri("/")
                    .body(BodyInserters.fromValue(todo))
                    .retrieve()
                    .bodyToMono(Todo.class);
    }

    public Mono<ResponseEntity<Void>> deleteTodo(int id) {
        
        return WebClient
                    .builder()
                    .baseUrl(baseUrl)
                    .build()
                    .delete()
                    .uri("/" + id)
                    .retrieve()
                    .toBodilessEntity();
                    
    }

    public Mono<Todo> updateTodo(Todo todo) {
        
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
