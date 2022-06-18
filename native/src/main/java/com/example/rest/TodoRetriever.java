package com.example.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import com.google.gson.Gson;

public class TodoRetriever {

    private final String baseUrl = "http://jsonplaceholder.typicode.com/todos/";

    public List<Todo> readAll() throws URISyntaxException, IOException, InterruptedException {

        var request = HttpRequest
                .newBuilder()
                .uri(new URI(baseUrl))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        var response = client.send(request, BodyHandlers.ofString());

        var todos = new Gson().fromJson(response.body(), Todo[].class);

        return List.of(todos);
    }

    public Todo readSingle(int id) throws URISyntaxException, IOException, InterruptedException {

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(new URI(baseUrl + id))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        Todo todo = new Gson().fromJson(response.body(), Todo.class);

        return todo;
    }

    public Todo create(Todo aTodo) throws URISyntaxException, IOException, InterruptedException {

        HttpRequest request = HttpRequest
                .newBuilder(new URI(baseUrl))
                .headers("content-type", "application/json")
                .POST(BodyPublishers.ofString(new Gson().toJson(aTodo)))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        return new Gson().fromJson(response.body(), Todo.class);
    }

    public int delete(int id) throws URISyntaxException, IOException, InterruptedException {

        HttpRequest request = HttpRequest
                .newBuilder(new URI(baseUrl + id))
                .DELETE()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        return response.statusCode();
    }

    public Todo update(Todo aTodo) throws URISyntaxException, IOException, InterruptedException {

        HttpRequest request = HttpRequest
                                .newBuilder(new URI(baseUrl + aTodo.getId()))
                                .headers("content-type", "application/json")
                                .PUT(BodyPublishers.ofString(new Gson().toJson(aTodo)))
                                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        return new Gson().fromJson(response.body(), Todo.class);
    }

}
