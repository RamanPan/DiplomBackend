package ru.ramanpan.petroprimoweb.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import ru.ramanpan.petroprimoweb.model.enums.DeterministicOption;
import ru.ramanpan.petroprimoweb.model.enums.Status;
import ru.ramanpan.petroprimoweb.model.enums.TestType;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "tests")
public class Test extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TestType testType;

    @Enumerated(value = EnumType.STRING)
    private DeterministicOption optionForDeterministicType;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private Double mark;

    private String author;

    private Integer numberQuestions;

    private Integer numberPasses;

    private Integer percentCulture;

    private Integer percentPolitic;

    private Integer percentEconomic;

    @Column(length = 1000)
    private String description;

    private String picture;

//    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,optional = false)
//    @JoinColumn(nullable = false,name = "user_id")
//    private User user;

    @OneToMany(mappedBy = "test",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Result> results = new ArrayList<>();

    @OneToMany(mappedBy = "test",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Question> questions = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Test test = (Test) o;
        return getId() != null && Objects.equals(getId(), test.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
