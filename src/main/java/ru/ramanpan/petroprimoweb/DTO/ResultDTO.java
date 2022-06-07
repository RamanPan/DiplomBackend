package ru.ramanpan.petroprimoweb.DTO;

import lombok.Data;
import lombok.ToString;
import ru.ramanpan.petroprimoweb.model.Test;

import java.util.Date;

@Data
@ToString
public class ResultDTO {
    private Long id;

    private Date created;

    private String description;

    private String header;

    private String picture;

    private Integer startCondition;

    private Integer endCondition;

    private Boolean correctness;

    private int number;

    private Long testLong;


}
