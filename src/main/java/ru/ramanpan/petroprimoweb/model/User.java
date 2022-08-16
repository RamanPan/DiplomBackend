package ru.ramanpan.petroprimoweb.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import ru.ramanpan.petroprimoweb.model.enums.Role;
import ru.ramanpan.petroprimoweb.model.enums.Status;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "date_register")
    private Date created;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false)
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(length = 1000)
    private String description;

    private String picture;

    private Integer countPassed;

    private Integer countCreated;

    private Integer countPassedCorrect;

    private Integer countPassedIncorrect;


    public User() {
        this.id = 0L;
    }

    //    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    @ToString.Exclude
//    private Set<Test> tests = new HashSet<>();
}
