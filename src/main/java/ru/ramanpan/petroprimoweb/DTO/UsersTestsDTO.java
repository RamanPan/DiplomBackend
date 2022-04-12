package ru.ramanpan.petroprimoweb.DTO;

import lombok.Data;
import ru.ramanpan.petroprimoweb.model.enums.Correctness;

@Data
public class UsersTestsDTO {
    private Long id;

    private Long test;

    private Long user;

    private String correctness;

    private Double mark;
}
