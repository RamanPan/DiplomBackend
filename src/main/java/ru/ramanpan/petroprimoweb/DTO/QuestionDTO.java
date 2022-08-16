package ru.ramanpan.petroprimoweb.DTO;


import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class QuestionDTO {
    private Long id;

    private Date created;

    private String statement;

    private String picture;

    private String type;

    private String category;

    private String difficult;

    private int typeNum;

    private int categoryNum;

    private int difficultNum;

    private int number;

    private Long testLong;

}
