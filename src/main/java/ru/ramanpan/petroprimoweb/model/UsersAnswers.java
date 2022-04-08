package ru.ramanpan.petroprimoweb.model;

import lombok.*;
import ru.ramanpan.petroprimoweb.model.enums.Correctness;


import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity
@Table
public class UsersAnswers extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "usertest_id",nullable = false)
    private UsersTests test;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "question_id",nullable = false)
    private Question question;

    private Boolean correct;
    @Column(nullable = false)
    private String answer;
}
