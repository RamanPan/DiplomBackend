package ru.ramanpan.petroprimoweb.DTO;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class AnswerDTO {
    private Long id;

    private Date created;

    private String statement;

    private Boolean correctness;

    private Long questionLong;

    private int number;
}
