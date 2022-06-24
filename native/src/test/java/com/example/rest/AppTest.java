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

    private TodoRetriever restApp;

    @BeforeAll
    public void setup() throws URISyntaxException {
        restApp = new TodoRetriever();

    }

    @Test
    public void readAllTodos() throws Exception {
        
        assertDoesNotThrow(() -> restApp.readAll());
        
        List<Todo> todos = restApp.readAll();
        
        assertNotNull(todos);
        assertNotEquals(0, todos.size());

    }

    @Test
    public void readSingleTodo() throws URISyntaxException, IOException, InterruptedException {
        assertDoesNotThrow(() -> restApp.readSingle(1));

        Todo todo = restApp.readSingle(1);

        assertNotNull(todo);
        assertEquals(1, todo.getId());
    }

    @Test
    public void createTodo() throws URISyntaxException, IOException, InterruptedException {

        Todo aTodo = new Todo(0, 0, "To read book", false);

        assertDoesNotThrow(() -> restApp.create(aTodo));

        Todo createdTodo = restApp.create(aTodo);

        assertNotNull(createdTodo);
        assertNotEquals(0, createdTodo.getId());
    }

    @Test
    public void deleteTodo() throws Exception {

        assertDoesNotThrow(() -> restApp.delete(1));
        assertEquals(200, restApp.delete(1));
    }

    @Test
    public void updateTodo() throws Exception {

        Todo aTodo = new Todo(0, 1, "To read book", true);

        assertDoesNotThrow(() -> restApp.update(aTodo));

        Todo updatedTodo = restApp.update(aTodo);
        assertEquals(aTodo.getTitle(), updatedTodo.getTitle());
    }
}
