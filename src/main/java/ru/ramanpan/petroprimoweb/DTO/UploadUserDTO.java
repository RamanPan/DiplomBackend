package ru.ramanpan.petroprimoweb.DTO;

import lombok.Data;

@Data
public class UploadUserDTO {
    private Long id;

    private String login;

    private String fullname;

    private String email;

    private String picture;

    private String password;

    private Integer countPassed;

    private Integer countCreated;

}
