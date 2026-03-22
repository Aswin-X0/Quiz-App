package com.example.Brainstorm.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Srequest {
    private String Username;
    private List<Arequest> answers;
}
