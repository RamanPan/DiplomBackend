package ru.ramanpan.petroprimoweb.DTO;



import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.ramanpan.petroprimoweb.model.Test;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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

    private Long test;

    private TestDTO t;

    private Set<AnswerDTO> answerDTOSet;

    public QuestionDTO(Long id, Date created, String statement, String picture, String type, String category, String difficult, Long test, Set<AnswerDTO> answerDTOSet) {
        this.id = id;
        this.created = created;
        this.statement = statement;
        this.picture = picture;
        this.type = type;
        this.category = category;
        this.difficult = difficult;
        this.test = test;
        this.answerDTOSet = answerDTOSet;
    }

    public QuestionDTO(Long id, Date created, String statement, String picture, String type, String category, String difficult, TestDTO t, Set<AnswerDTO> answerDTOSet) {
        this.id = id;
        this.created = created;
        this.statement = statement;
        this.picture = picture;
        this.type = type;
        this.category = category;
        this.difficult = difficult;
        this.t = t;
        this.answerDTOSet = answerDTOSet;
    }

    public QuestionDTO(String statement, String picture, String type, String category, String difficult, Long test) {
        this.statement = statement;
        this.picture = picture;
        this.type = type;
        this.category = category;
        this.difficult = difficult;
        this.test = test;
    }

    public QuestionDTO() {
    }
}
