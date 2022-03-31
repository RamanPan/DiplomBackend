package ru.ramanpan.petroprimoweb.service;

import ru.ramanpan.petroprimoweb.model.User;

import java.util.List;

public interface UserService {
    User register(User user);
    List<User> getAll();
    User findByNickname(String username);
    User findById(Long id);
    void delete(Long id);

}