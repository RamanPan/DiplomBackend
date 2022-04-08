package ru.ramanpan.petroprimoweb.model;

import lombok.*;
import org.hibernate.Hibernate;
import ru.ramanpan.petroprimoweb.model.enums.Correctness;
import ru.ramanpan.petroprimoweb.model.enums.TestStatus;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table
@Entity(name = "users_tests")
public class UsersTests extends BaseEntity{

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "test_id",nullable = false)
    private Test test;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false,length = 15)
    private Correctness correctness;

    private Double mark;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UsersTests that = (UsersTests) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
