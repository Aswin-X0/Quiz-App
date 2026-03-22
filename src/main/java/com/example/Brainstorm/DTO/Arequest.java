package com.example.Brainstorm.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Arequest {
    
    private Long questionId;
    private String selectedAnswer;
}
