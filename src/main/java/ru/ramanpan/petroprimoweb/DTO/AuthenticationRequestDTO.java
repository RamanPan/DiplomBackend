package ru.ramanpan.petroprimoweb.DTO;


import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String login;
    private String password;
}
