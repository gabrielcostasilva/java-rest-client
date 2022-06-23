# Spring OpenFeign Support Example
This project shows how to use the [Spring Cloud](https://spring.io/projects/spring-cloud-openfeign) support for the declarative [OpenFeign Client API](https://github.com/OpenFeign/feign) to call REST APIs. 

With the Spring Cloud support, one can easily create a REST client by decorating an interface with the well known Spring request mappings.

## Project Overview
This project consists of four classes. [`Todo`](./src/main/java/com/example/feign/Todo.java) is a Java POJO that represents a single todo. It uses the [lombok library](https://projectlombok.org) to simplify the getters/setters management. 

[`TodoRetriever`](./src/main/java/com/example/feign/TodoRetriever.java) is Java interface responsible for setting CRUD operations. This is where the annotations are added. 

[`Config`](./src/main/java/com/example/feign/Config.java) is the class responsible for creating the JSON encoder/decoder used by OpenFeign.

As this is a tradicional Spring Boot Web application, there is also a class responsible for starting the application ([`FeignApplication`](./src/main/java/com/example/feign/FeignApplication.java)). To enable scanning Java interfaces declaring OpenFeign clients, we have to add `org.springframework.cloud.openfeign.EnableFeignClients` annotation to the [`FeignApplication`](./src/main/java/com/example/feign/FeignApplication.java) class.

## TodoRetriever
The code below fully shows the TodoRetriever code.

```java
@FeignClient(
    name = "todo-client", 
    url = "https://jsonplaceholder.typicode.com/todos", 
    configuration = Config.class) // (1)
public interface TodoRetriever {
    
    @GetMapping(consumes = "application/json;charset=utf-8") // (3)
    public List<Todo> readAll(); // (2)

    @GetMapping("/{id}") // (3)
    public Todo readSingle(@PathVariable int id); // (2)

    @PostMapping(consumes = "application/json;charset=utf-8", 
        produces = "application/json;charset=utf-8") // (3)
    public Todo create(@RequestBody Todo todo); // (2)
    
    @DeleteMapping(value = "/{id}", produces = "application/json;charset=utf-8") // (3)
    public Todo delete(@PathVariable int id); // (2)

    @PutMapping(value = "{id}", 
        consumes = "application/json;charset=utf-8", 
        produces = "application/json;charset=utf-8") // (3)
    public Todo update(@PathVariable int id, @RequestBody Todo todo); // (2)
}
```
1. `org.springframework.cloud.openfeign.FeignClient` declares this interface as an OpenFeign client. It has (i) a `name` that is used to identify this client; (ii) a `url` that represents the target endpoint; and (iii) a configuration class that is used to set JSON encoders/decoders.

2. CRUD methods that declares URL parameters, request body, and request/response types. Note that tradicional [Spring Web Controller annotations](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-controller) are used.

3. Request type annotations. Again, tradicional [Spring Web Controller annotations](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-controller) are used. Notice that annotations are complemented with `consumes` and `produces` parameters. That is necessary because the [JSON Placeholder TODO API](https://jsonplaceholder.typicode.com) used consumes/produces JSON request/responses.

## Encoders & Decoders
By default, OpenFeign clients use primitive types. Receiving a String as result of a request is straightforward. But, when an API uses complex types, like JSON, encoders & decoders are required in order to translate the request/response.

Luckily, there are libraries for encoders/decoders - and Spring makes it [even easier](https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/#spring-cloud-feign-overriding-defaults) for us to use them.

The [`Config`](./src/main/java/com/example/feign/Config.java) class acts as a `@Configuration` class. Two beans return `feign.codec.Encoder` and `feign.codec.Decoder` implementations to be used in our project. 

We use `feign.gson.GsonEncoder` and `feign.gson.GsonDecoder` as encoder and decoder, respectively.

## Dependencies
In addition to Spring Cloud OpenFeign libraries, we use `org.projectlombok.lombok` library to simplify the POJO TODO management.

Because we are dealing with JSON requests, we also use the `io.github.openfeign.feign-gson` library to encode and decode JSON requests.

## Further Reference
- [Introduction to Spring Cloud OpenFeign](https://www.baeldung.com/spring-cloud-openfeign)