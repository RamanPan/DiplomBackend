package ru.ramanpan.petroprimoweb.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserResultDTO {
    private Long id;

    private Date created;

    private String header;

    private String description;

    private String name;

    private String picture;

    private Double result;

    private Boolean correctness;

}
