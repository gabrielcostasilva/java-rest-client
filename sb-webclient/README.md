# Reactive WebClient Example
This project shows how to use the reactive WebClient to call REST APIs.

## Project Overview
This project consists of two classes. [`Todo`](./src/main/java/com/example/demo/Todo.java) is a Java _record_ that represents a single todo. [`TodoRetriever`](./src/main/java/com/example/demo/TodoRetriever.java) is Java class responsible for implementing the WebClient library for CRUD operations.

The CRUD operations are straightforward. They use the same `baseUrl`, which refers to the JSON Placeholder TODO fake API. The `WebClient` uses this `baseUrl` to build a new client, as the snippet below shows:

```java
// (...)

WebClient
    .builder()
    .baseUrl(this.baseUrl)
    .build()
    . // (...)
```

The procedure above is repeated for each CRUD operation. Next, one should set the HTTP verb to be used. This choice varies according to the REST API. For the TODO API:

- _create_ operation uses POST;
- _read_ operations use GET;
- _update_ operation uses PUT;
- _delete_ operation uses DELETE.

The TODO API also supports PATCH, but we did not implemented it here. Feel free to add the PATCH code and submit a pull request ;)

The snippet below examplifies the use of a HTTP GET for the _read_ operation.

```java
    // (...)
    .build()
    .get()
    . // (...)
```

Sometimes, the `baseUrl` needs extra information. For instance, consider the `readSingle` operation. This operation reads a single TODO based on its unique `id`. In this case, the TODO API requires the `id` to be added to the URL as path. 

The `WebClient` supports it with the method `uri(String)`, as the code below shows.

```java
    // (...)
    .build()
    .get()
    .uri("/" + id)
    . // (...)
```

The last part is the information retrieval. This is done by calling the `retrieve()` method, followed by how the response is extracted. In our example, we have three cases:

- `.bodyToFlux(Todo.class)` used for the _readAll_ operation because it returns a set of TODOs.
- `.bodyToMono(Todo.class)` used for _update_, _create_ and _readSingle_ operations because they return a single TODO.
- `.toEntity(Todo.class)` used for the _delete_ operation because it returns no data. In this case, we access the response object to check the return status code.

_Create_ and _update_ operations require a body with data. This is done with the `body(org.springframework.web.reactive.function.BodyInserters)` method. The `org.springframework.web.reactive.function.BodyInserters` has convenience methods for converting the TODO. The code below shows the _update_ operation. 

```java
return WebClient
            .builder()
            .baseUrl(baseUrl)
            .build()
            .put()
            .uri("/" + todo.id())
            .body(BodyInserters.fromValue(todo))
            .retrieve()
            .bodyToMono(Todo.class);
```

Please checkout [tests](./src/test/java/com/example/demo/DemoApplicationTests.java) to see how it works.

## Dependencies
As the project uses Spring Boot, both required dependencies belong to `org.springframework.boot` groupId. Whereas `spring-boot-starter-webflux` artefactId provides classes for the WebClient library, `spring-boot-starter-test` artefactId enables creating and running synchronous tests.

> It is important to highlight that asynchronous tests rely on an extra dependency. Checkout [this project](https://github.com/gabrielcostasilva/reactivity-examples.git) to know more about reactive web apps with WebFlux.