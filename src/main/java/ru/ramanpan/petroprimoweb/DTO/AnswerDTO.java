package ru.ramanpan.petroprimoweb.DTO;

import lombok.Data;
import lombok.ToString;
import ru.ramanpan.petroprimoweb.model.Question;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@ToString
public class AnswerDTO {
    private Long id;

    private Date created;

    private String statement;

    private Boolean correctness;

    private Long question;

    private QuestionDTO q;
    public AnswerDTO(Long id, Date created, String statement, Boolean correctness, Long question) {
        this.id = id;
        this.created = created;
        this.statement = statement;
        this.correctness = correctness;
        this.question = question;
    }

    public AnswerDTO(String statement, Boolean correctness, Long question) {
        this.statement = statement;
        this.correctness = correctness;
        this.question = question;
    }

    public AnswerDTO(Long id, Date created, String statement, Boolean correctness, QuestionDTO q) {
        this.id = id;
        this.created = created;
        this.statement = statement;
        this.correctness = correctness;
        this.q = q;
    }

    public AnswerDTO() {
    }
}
