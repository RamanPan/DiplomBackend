package ru.ramanpan.petroprimoweb.DTO;

import com.fasterxml.jackson.databind.JsonSerializable;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import ru.ramanpan.petroprimoweb.model.enums.Role;
import ru.ramanpan.petroprimoweb.model.enums.Status;

import javax.persistence.*;
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

    private String token;

    public ActualUserDTO() {
    }

    public ActualUserDTO(Long id, Date created, String nickname, String fullname, String email, String role, String description, String picture, String token) {
        this.id = id;
        this.created = created;
        this.nickname = nickname;
        this.fullname = fullname;
        this.email = email;
        this.role = role;
        this.description = description;
        this.picture = picture;
        this.token = token;
    }
}
