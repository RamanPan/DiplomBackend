package ru.ramanpan.petroprimoweb.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.ramanpan.petroprimoweb.model.BaseEntity;
import ru.ramanpan.petroprimoweb.model.enums.Correctness;

import javax.persistence.*;

@Entity
@Table
@ToString
@Getter
@RequiredArgsConstructor
@Setter
public class Answer extends BaseEntity {

    @Column(nullable = false)
    private String statement;

    private Boolean is_correct;

    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "question_id",nullable = false)
    private Question question;

}
