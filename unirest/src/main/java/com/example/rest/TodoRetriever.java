package com.example.rest;

import java.util.List;

import kong.unirest.GenericType;
import kong.unirest.Unirest;

public class TodoRetriever {

    public TodoRetriever() {
        Unirest.config().defaultBaseUrl("http://jsonplaceholder.typicode.com/todos/");
    }

    public List<Todo> readAll() {

        return Unirest
                    .get("/")
                    .asObject(new GenericType<List<Todo>>(){})
                    .getBody();
    }

    public Todo readSingle(int id) {

        return Unirest
                    .get("/{id}")
                    .routeParam("id", String.valueOf(id))
                    .asObject(Todo.class)
                    .getBody();
    }

    public Todo create(Todo aTodo) {

        return Unirest
                    .post("/")
                    .header("Content-Type", "application/json")
                    .body(aTodo)
                    .asObject(Todo.class)
                    .getBody();
    }

    public int delete(int id) {

        return Unirest
                    .delete("/{id}")
                    .routeParam("id", String.valueOf(id))
                    .asEmpty()
                    .getStatus();
    }

    public Todo update(Todo aTodo) {

        return Unirest
                    .put("/{id}")
                    .routeParam("id", String.valueOf(aTodo.getId()))
                    .header("Content-Type", "application/json")
                    .body(aTodo)
                    .asObject(Todo.class)
                    .getBody();
    }

}
