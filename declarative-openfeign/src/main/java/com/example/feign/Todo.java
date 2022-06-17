package com.example.feign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    
    private int id;
    private int userId;
    private String title;
    private boolean completed;

}
