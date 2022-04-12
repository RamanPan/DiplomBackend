package ru.ramanpan.petroprimoweb.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import ru.ramanpan.petroprimoweb.model.BaseEntity;
import ru.ramanpan.petroprimoweb.model.enums.Correctness;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@ToString
@Getter
@RequiredArgsConstructor
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "date_register")
    private Date created;

    @Column(nullable = false)
    private String statement;

    private Boolean correctness;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id",nullable = false)
    private Question question;

}
