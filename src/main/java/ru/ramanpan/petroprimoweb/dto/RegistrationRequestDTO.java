package ru.ramanpan.petroprimoweb.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RegistrationRequestDTO {
    private String role;

    private String nickname;

    private String password;

    private String fullname;

    private String email;


}
