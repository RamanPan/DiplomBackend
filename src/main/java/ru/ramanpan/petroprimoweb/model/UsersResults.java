package ru.ramanpan.petroprimoweb.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.ramanpan.petroprimoweb.model.enums.Correctness;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity
@Table
public class UsersResults extends BaseEntity{
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "usertest_id",nullable = false)
    private UsersTests test;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "result_id",nullable = false)
    private Result result;
}
