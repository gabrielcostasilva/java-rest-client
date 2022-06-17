package com.example.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "todo-client", 
    url = "https://jsonplaceholder.typicode.com/todos", 
    configuration = Config.class)
public interface TodoRetriever {
    
    @GetMapping(consumes = "application/json;charset=utf-8")
    public List<Todo> readAll();

    @GetMapping("/{id}")
    public Todo readSingle(@PathVariable int id);

    @PostMapping(consumes = "application/json;charset=utf-8", 
        produces = "application/json;charset=utf-8")
    public Todo create(@RequestBody Todo todo);
    
    @DeleteMapping(value = "/{id}", produces = "application/json;charset=utf-8")
    public Todo delete(@PathVariable int id);

    @PutMapping(value = "{id}", 
        consumes = "application/json;charset=utf-8", 
        produces = "application/json;charset=utf-8")
    public Todo update(@PathVariable int id, @RequestBody Todo todo);
}
