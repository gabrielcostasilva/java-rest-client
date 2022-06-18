# Native JDK HTTP Support Example
This project shows how to use the JDK HTTP Client API to call REST APIs. The HTTP Client was [officially released with JDK 11](https://openjdk.org/jeps/321). As the [WebClient](../sb-webclient/), it is a non-blocking library.

The HTTP Client library for REST APIs [consists of three interfaces](http://cr.openjdk.java.net/~chegar/httpclient/02/javadoc/api/java.net.http/java/net/http/package-summary.html) from `java.net.http` package, as follows:

- `HttpRequest` represents an HTTP request.
- `HttpClient` handles the request and produces a response.
- `HttpResponse` represents a response from a request.

## Project Overview
This project consists of two classes. [`Todo`](./src/main/java/com/example/rest/Todo.java) is a Java POJO that represents a single todo. It uses the [lombok library](https://projectlombok.org) to simplify the getters/setters management. [`TodoRetriever`](./src/main/java/com/example/rest/TodoRetriever.java) is Java class responsible for implementing the HTTP Client library for CRUD operations.

CRUD operations are straightforward. They use the same `baseUrl`, which refers to the JSON Placeholder TODO fake API. 

The process in each operation is always the same: 

1. Create an `HttpRequest` using the base URL;
2. Create an `HttpClient` to submitting the request;
3. Receive and return the response from `HttpResponse`.

The snippet below illustrates the basic process with the _readAll_ operation:

```java
var request = HttpRequest
                .newBuilder()
                .uri(new URI(baseUrl))
                .GET()
                .build(); // (1)

HttpClient client = HttpClient.newHttpClient(); // (2)
var response = client.send(request, BodyHandlers.ofString()); // (2, 3)

var todos = new Gson().fromJson(response.body(), Todo[].class); // (3)

return List.of(todos); //(3)
```

Notice the use of `Gson` library in step 3 to converting the JSON response to a TODO POJO.

Of course, there are slight variations according to the HTTP metod used. These variations are detailed in sections below.

### Create & update

Whereas the `create(Todo)` operation submits a `POST`, the `update(Todo)` operation submits a `PUT` request.  However, in both cases, the JSON Placeholder TODO API requires a JSON body with data. 

Therefore, the request uses `headers()` method to set the content type to JSON. In addition, the `POST()` method requires an `HttpRequest.BodyPublisher` implementation to converting the data to be sent.

The code below shows the `create(Todo)` method body.

```java
HttpRequest request = HttpRequest
    .newBuilder(new URI(baseUrl))
    .headers("content-type", "application/json")
    .POST(BodyPublishers.ofString(new Gson().toJson(aTodo))) // (1)
    .build();

HttpClient client = HttpClient.newHttpClient();
HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

return new Gson().fromJson(response.body(), Todo.class); // (2)
```

In (1), `HttpRequest.BodyPublishers` provides an implementation that converts data from a `String`. As the JSON Placeholder TODO API expects a JSON, we first need to convert the TODO POJO into a JSON. The _Gson_ library is used for this purpose. 

The `create(Todo)` method returns a `Todo` instance, representing the Todo that was created. The `response` variable holds the API response object. 

`response.body()` accesses the response body content. In this case, the response body content is a JSON with the TODO data returned from the JSON Placeholder TODO API.

In (2), the JSON response is converted into a Todo POJO instance by using the _Gson_ library. The Todo POJO is then returned as the `create()` method result.

### Delete
The JSON Placeholder TODO API does not return any data for the `DELETE` method. Therefore, our `delete(int)` method needs to check the response status code in order to determine whether the request was successful (2). The snippet clarifies that.

```java
// (...)

HttpResponse<String> response = client.send(request, BodyHandlers.ofString()); // (1)

return response.statusCode(); // (2)
```

Please note that we always convert the body of a response into `String` (1). The same strategy is used for all operations, even here, when we know the result is going to be empty. 

### Exceptions
Note that `HttpRequest`, `HttpClient` and `HttpReponse` may raise exceptions, which are just passed along by our CRUD operations.

## Dependencies
There is no need for additional dependencies in order to use the HTTP Client. However, this project uses `com.google.code.gson.gson` library to convert from/to JSON. In addition, we use `org.projectlombok.lombok` library to simplify the POJO TODO management.

Note that JUnit 5 libraries are also in the project to enable testing.

## Further Reference
- [Examples and Recipes](https://openjdk.org/groups/net/httpclient/recipes-incubating.html)
- [Introduction to the Java HTTP Client](https://openjdk.org/groups/net/httpclient/intro.html)
- [Introduction to the Java 11 HTTP Client with Chris Hegarty](https://www.youtube.com/watch?v=sZSdWq490Vw)
- [Handling Response Data with the Java 11 HTTP Client with Chris Hegarty](https://www.youtube.com/watch?v=qiaC0QMLz5Y)