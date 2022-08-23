package ru.ramanpan.petroprimoweb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.dto.RegistrationRequestDTO;
import ru.ramanpan.petroprimoweb.exceptions.NotFoundException;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.model.enums.Role;
import ru.ramanpan.petroprimoweb.model.enums.Status;
import ru.ramanpan.petroprimoweb.repository.UserRepo;
import ru.ramanpan.petroprimoweb.service.UserService;
import ru.ramanpan.petroprimoweb.util.Constants;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;


    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public void register(RegistrationRequestDTO request) {
        User user = new User();
        user.setNickname(request.getNickname());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFullname(request.getFullname());
        user.setCountPassed(0);
        user.setCountCreated(0);
        if (request.getRole().equals("Преподаватель")) user.setRole(Role.ROLE_PROFESSOR);
        else user.setRole(Role.ROLE_STUDENT);
        user.setPassword(encoder.encode(request.getPassword()));
        user.setCountPassedIncorrect(0);
        user.setCountPassedCorrect(0);
        user.setStatus(Status.ACTIVE);
        user.setPicture(" ");
        user.setDescription(" ");
        user.setCreated(new Date());
        userRepo.save(user);
    }

    @Override
    public void update(User user) {
        userRepo.save(user);
    }

    @Override
    public Long changePassword(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user).getId();
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User findByNickname(String username) {
        return userRepo.findByNickname(username).orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));
    }

    @Override
    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }
}
