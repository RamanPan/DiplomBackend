package ru.ramanpan.petroprimoweb.service;

import ru.ramanpan.petroprimoweb.DTO.RegistrationRequestDTO;
import ru.ramanpan.petroprimoweb.model.User;

import java.util.List;

public interface UserService {
    void register(RegistrationRequestDTO request);

    List<User> getAll();

    User findByNickname(String username);

    User findById(Long id);

    void delete(Long id);

    void update(User user);

    User save(User user);

    Long changePassword(User user);
}
