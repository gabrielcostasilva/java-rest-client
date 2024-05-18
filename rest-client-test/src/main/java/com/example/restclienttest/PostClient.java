package com.example.restclienttest;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import static org.springframework.web.client.RestClient.builder;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;

@Component
public class PostClient {

    private final RestClient restClient;

    public PostClient(RestClient.Builder restClient) {
        this.restClient = builder()
                            .baseUrl("https://jsonplaceholder.typicode.com")
                            .build();

    }

    public List<Post> findAll() {
        return restClient
                    .get()
                    .uri("/posts")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

    }
}
