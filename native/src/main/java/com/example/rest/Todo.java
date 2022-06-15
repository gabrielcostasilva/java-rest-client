package com.example.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Todo {

    private int userId;
    private int id;
    private String title;
    private String body;
} 

