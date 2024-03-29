package ru.ramanpan.petroprimoweb.dto;

import lombok.Data;
import lombok.ToString;
import ru.ramanpan.petroprimoweb.model.User;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class ActualUserDTO implements Serializable {

    private Long id;

    private Date created;

    private String nickname;

    private String fullname;

    private String email;

    private String role;

    private String description;

    private String picture;

    private Integer countPassed;

    private Integer countCreated;

    private String token;


    public ActualUserDTO(Long id, Date created, String nickname, String fullname, String email, String role, String description, String picture, Integer countPassed, Integer countCreated, String token) {
        this.id = id;
        this.created = created;
        this.nickname = nickname;
        this.fullname = fullname;
        this.email = email;
        this.role = role;
        this.description = description;
        this.picture = picture;
        this.countPassed = countPassed;
        this.countCreated = countCreated;
        this.token = token;
    }



    public static ActualUserDTO mapToDTO(User user) {
        return new ActualUserDTO(user.getId(), user.getCreated(), user.getNickname(), user.getFullname(), user.getEmail(), user.getRole().name(), user.getDescription(), user.getPicture(), user.getCountPassed(), user.getCountCreated(), "");
    }
}
