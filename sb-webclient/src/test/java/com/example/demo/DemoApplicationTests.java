package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DemoApplicationTests {

	private TodoRetriever retriever;

	@BeforeAll
	public void setup() {

		retriever = new TodoRetriever();
	}

	@Test
	public void readAllTodos() {
		
		var todos = retriever
						.readAllTodos()
						.toStream()
						.collect(Collectors.toList());

		assertNotNull(todos);
		assertNotEquals(0, todos.size());
	}

	@Test
	public void readSingleTodo() {
		
		var todo = retriever
						.readSingleTodo()
						.block();
		
		assertNotNull(todo);
		assertEquals(1, todo.id());
	}

	@Test
	public void createTodo() {
		
		var todo = retriever
						.createTodo(new Todo(10, 10, "To test the app", false))
						.block();
		
		assertNotNull(todo);
		assertEquals("To test the app", todo.title());
	}

	@Test
	public void deleteTodo() {
		
		var response = retriever
						.deleteTodo(1)
						.block();

		assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	public void updateTodo() {
		
		var todo = retriever
						.updateTodo(new Todo(1, 1, "To test the app", false))
						.block();

		assertNotNull(todo);
		assertEquals("To test the app", todo.title());
	}

}
