package ru.ramanpan.petroprimoweb.dto;


import lombok.Data;

import java.util.List;

@Data
public class UserAnswerDTO {
    private Long id;

    private String statement;

    private String answer;

    private String picture;

    private List<String> rightAnswer;

    private Boolean correctness;
}
