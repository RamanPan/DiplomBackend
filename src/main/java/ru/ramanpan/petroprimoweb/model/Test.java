package ru.ramanpan.petroprimoweb.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import ru.ramanpan.petroprimoweb.model.enums.TestType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "tests")
public class Test extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TestType testType;

    private Double mark;

    private String author;

    private Integer number_questions;

    private Integer number_passes;

    @Column(length = 1000)
    private String description;

    private String picture;

//    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,optional = false)
//    @JoinColumn(nullable = false,name = "user_id")
//    private User user;

    @OneToMany(mappedBy = "test",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Result> results = new HashSet<>();

    @OneToMany(mappedBy = "test",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Question> questions = new HashSet<>();


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

//    @JsonIgnore
//    public User getUser() {
//        return user;
//    }
}
