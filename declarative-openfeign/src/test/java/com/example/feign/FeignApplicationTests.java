package com.example.feign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootApplication
@ExtendWith(SpringExtension.class)
class FeignApplicationTests {

	@Autowired
	private TodoRetriever todoRetriever;

	@Test
	public void readAllTodos() {
		var result = todoRetriever.readAll();

		assertNotNull(result);
		assertNotEquals(0, result.size());
	}

	@Test
	public void readSingleTodo() {
		var result = todoRetriever.readSingle(1);

		assertNotNull(result);
		assertNotEquals(0, result.getId());
	}

	@Test
	public void createTodo() {
		String title = "New Todo";
		var result = todoRetriever.create(new Todo(0, 0, title, false));

		assertNotNull(result);
		assertEquals(title, result.getTitle());
	}

	@Test
	public void deleteTodo() {
		var result = todoRetriever.delete(1);

		assertNull(result.getTitle());
	}

	@Test
	public void updateTodo() {
		String title = "Updated Todo";
		var result = todoRetriever.update(1, new Todo(1, 0, title, false));

		assertNotNull(result);
		assertEquals(title, result.getTitle());
	}

}
