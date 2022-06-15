package com.example.rest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppTest {

    private App restApp;

    @BeforeAll
    public void setup() throws URISyntaxException {
        restApp = new App();

    }

    @Test
    public void readAllTodos() throws Exception {
        
        assertDoesNotThrow(() -> restApp.readAllTodos());
        
        List<Todo> todos = restApp.readAllTodos();
        
        assertNotNull(todos);
        assertNotEquals(0, todos.size());

    }

    @Test
    public void readSingleTodo() throws URISyntaxException, IOException, InterruptedException {
        assertDoesNotThrow(() -> restApp.readTodo(1));

        Todo todo = restApp.readTodo(1);

        assertNotNull(todo);
        assertEquals(1, todo.getId());
    }

    @Test
    public void createTodo() throws URISyntaxException, IOException, InterruptedException {

        Todo aTodo = new Todo(0, 0, "To read book", "To buy a top rated book to read");

        assertDoesNotThrow(() -> restApp.createTodo(aTodo));

        Todo createdTodo = restApp.createTodo(aTodo);

        assertNotNull(createdTodo);
        assertNotEquals(0, createdTodo.getId());
    }

    @Test
    public void deleteTodo() throws Exception {

        assertDoesNotThrow(() -> restApp.deleteTodo(1));
        assertEquals(200, restApp.deleteTodo(1));
    }

    @Test
    public void updateTodo() throws Exception {

        Todo aTodo = new Todo(0, 1, "To read book", "To buy a top rated book to read");

        assertDoesNotThrow(() -> restApp.updateTodo(aTodo));

        Todo updatedTodo = restApp.updateTodo(aTodo);
        assertEquals(aTodo.getTitle(), updatedTodo.getTitle());
    }
}
