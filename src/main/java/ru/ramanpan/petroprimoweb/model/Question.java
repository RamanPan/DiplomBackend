package ru.ramanpan.petroprimoweb.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import ru.ramanpan.petroprimoweb.model.enums.DifficultyQuestion;
import ru.ramanpan.petroprimoweb.model.enums.QuestionCategory;
import ru.ramanpan.petroprimoweb.model.enums.QuestionType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table
public class Question extends BaseEntity{

    @Column(length = 1000,nullable = false)
    private String statement;

    private String picture;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20,nullable = false)
    private QuestionType type;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private QuestionCategory category;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private DifficultyQuestion difficult;

    @ManyToOne(optional = false)
    @JoinColumn(name = "test_id",nullable = false)
    private Test test;

    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Answer> answers = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Question question = (Question) o;
        return getId() != null && Objects.equals(getId(), question.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
