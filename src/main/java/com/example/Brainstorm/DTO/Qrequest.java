package com.example.Brainstorm.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qrequest {
    
    private String title;
    private String description;
    private String category;
    
}
