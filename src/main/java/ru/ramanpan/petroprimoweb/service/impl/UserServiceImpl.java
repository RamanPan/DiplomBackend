package ru.ramanpan.petroprimoweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.model.enums.Status;
import ru.ramanpan.petroprimoweb.repository.UserRepo;
import ru.ramanpan.petroprimoweb.service.UserService;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public void register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCountPassedIncorrect(0);
        user.setCountPassedCorrect(0);
        user.setStatus(Status.ACTIVE);
        user.setPicture(" ");
        user.setDescription(" ");
        user.setCreated(new Date());
        System.out.println(user);
        userRepo.save(user);
    }
    @Override
    public User update(User user) {
        return userRepo.save(user);
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
        return userRepo.findByNickname(username).orElse(null);
    }

    @Override
    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }
}
