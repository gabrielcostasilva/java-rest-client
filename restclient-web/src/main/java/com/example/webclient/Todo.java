package com.example.webclient;

public record Todo(
    Integer userId,
    Integer id,
    String title,
    Boolean completed
) {}
