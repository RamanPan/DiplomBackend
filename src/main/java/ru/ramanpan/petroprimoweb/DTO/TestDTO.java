package ru.ramanpan.petroprimoweb.DTO;

import lombok.Data;
import lombok.ToString;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.Result;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Data
@ToString
public class TestDTO {
    private Long id;

    private Date created;

    private String name;

    private String testType;

    private Double mark;

    private Integer numberQuestions;

    private Integer numberPasses;

    private String author;

    private String description;

    private String picture;

    private Set<ResultDTO> results;

    private Set<QuestionDTO> questions;

    public TestDTO(Long id, Date created, String name, String testType, Double mark, Integer numberQuestions, Integer numberPasses, String author, String description, String picture, Set<ResultDTO> results, Set<QuestionDTO> questions) {
        this.id = id;
        this.created = created;
        this.name = name;
        this.testType = testType;
        this.mark = mark;
        this.numberQuestions = numberQuestions;
        this.numberPasses = numberPasses;
        this.author = author;
        this.description = description;
        this.picture = picture;
        this.results = results;
        this.questions = questions;
    }

    public TestDTO(String name, String testType, String author, String description, String picture) {
        this.name = name;
        this.testType = testType;
        this.author = author;
        this.description = description;
        this.picture = picture;
    }

    public TestDTO() {
    }
}
