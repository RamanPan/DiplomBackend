package ru.ramanpan.petroprimoweb.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class QuestDTO {
    private Long id;

    private Date created;

    private String statement;

    private String picture;

    private String type;

    private String category;

    private String difficult;
}
