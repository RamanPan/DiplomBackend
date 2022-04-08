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

    private Long test;

    private TestDTO t;
    public ResultDTO(Long id, Date created, String description, String header, String picture, Long test) {
        this.id = id;
        this.created = created;
        this.description = description;
        this.header = header;
        this.picture = picture;
        this.test = test;
    }

    public ResultDTO(String description, String header, String picture, Long test) {
        this.description = description;
        this.header = header;
        this.picture = picture;
        this.test = test;
    }

    public ResultDTO(Long id, Date created, String description, String header, String picture, TestDTO t) {
        this.id = id;
        this.created = created;
        this.description = description;
        this.header = header;
        this.picture = picture;
        this.t = t;
    }
}
