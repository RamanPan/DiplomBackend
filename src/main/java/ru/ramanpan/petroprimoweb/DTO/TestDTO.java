package ru.ramanpan.petroprimoweb.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TestDTO {
    private Long id;

    private Long userId;

    private String name;

    private Double mark;

    private String created;

    private String testType;

    private String optionForDeterministicType;

    private String author;

    private String numberQuestions;

    private Integer numberPasses;

    private Integer percentCulture;

    private Integer percentPolitic;

    private Integer percentEconomic;

    private String description;

    private String picture;
}
