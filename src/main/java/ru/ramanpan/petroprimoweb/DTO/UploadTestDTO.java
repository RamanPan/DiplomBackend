package ru.ramanpan.petroprimoweb.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UploadTestDTO {
    private Long id;

    private String name;

    private Double mark;

    private Integer numberQuestions;

    private Integer numberPasses;

    private String description;

    private String picture;
}
