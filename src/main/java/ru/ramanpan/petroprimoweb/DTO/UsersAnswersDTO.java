package ru.ramanpan.petroprimoweb.DTO;

import lombok.Data;

@Data
public class UsersAnswersDTO {
    private Long id;

    private Long user;

    private Long question;

    private Long userTest;

    private Boolean correct;

    private String answer;
}
