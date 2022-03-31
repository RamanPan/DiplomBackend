package ru.ramanpan.petroprimoweb.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.ramanpan.petroprimoweb.model.enums.QuestionType;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table
public class Result extends BaseEntity {
    @Column(length = 1000,nullable = false)
    private String header;

    @Column(length = 1000)
    private String description;

    private String picture;

    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "test_id",nullable = false)
    private Test test;

}
