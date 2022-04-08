package ru.ramanpan.petroprimoweb.DTO;

import lombok.Data;

@Data
public class CreateTestDTO {
    private String name;
    private String testType;
    private String author;
    private String description;
    private String picture;

}
