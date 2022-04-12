package ru.ramanpan.petroprimoweb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TestCardDTO {
    private Long id;

    private Date created;

    private String name;

    private String testType;

    private Double mark;

    private Integer numberQuestions;

    private Integer numberPasses;

    private String author;

    private String description;

    private String picture;
}