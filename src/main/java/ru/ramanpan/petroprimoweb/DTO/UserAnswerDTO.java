package ru.ramanpan.petroprimoweb.DTO;


import lombok.Data;

@Data
public class UserAnswerDTO {
    private Long id;

    private String statement;

    private String answer;

    private String rightAnswer;

    private Boolean correctness;
}
