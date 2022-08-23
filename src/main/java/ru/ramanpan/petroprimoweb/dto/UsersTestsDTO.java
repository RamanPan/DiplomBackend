package ru.ramanpan.petroprimoweb.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UsersTestsDTO {
    private Long id;

    private Long test;

    private Long user;

    private String correctness;

    private Double mark;
}
