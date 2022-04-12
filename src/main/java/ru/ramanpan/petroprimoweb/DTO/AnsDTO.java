package ru.ramanpan.petroprimoweb.DTO;

import lombok.Data;

import java.util.Date;
@Data
public class AnsDTO {
    private Long id;

    private Date created;

    private String statement;

}
