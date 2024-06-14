# Java REST Client Examples
This repository groups examples of using different REST Java clients. Examples use the [JSON Placeholder](https://jsonplaceholder.typicode.com) TODO fake API as a target for CRUD operations.

> You may want to explore the [Spring Boot Webclient](https://github.com/gabrielcostasilva/sb-rest-webclient.git) if you use Spring Boot.

## Overview of Projects
Each folder groups a single REST client as follows:

- [_WebClient_](./sb-webclient/) uses the Spring WebFlux reactive support to call REST APIs.

- [_HTTP Client_](./native/) is based on JDK direct support to call HTTP endpoints.

- [_OpenFeign Spring Support_](./declarative-openfeign/) offers a declarative way to call REST APIs. This project uses the [Spring Cloud OpenFeign](https://spring.io/projects/spring-cloud-openfeign) support instead of the [OpenFeign](https://github.com/OpenFeign/feign.git) native implementation.

- [_Unirest_](./unirest/) uses the Java version of the [Unirest library](http://kong.github.io/unirest-java/) to perform REST calls.

