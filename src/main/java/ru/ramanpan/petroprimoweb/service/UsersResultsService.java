package ru.ramanpan.petroprimoweb.service;

import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.model.UsersResults;
import ru.ramanpan.petroprimoweb.model.UsersTests;

import java.util.List;

public interface UsersResultsService {
    List<UsersResults> findAll();
    UsersResults findById(Long id);
    void deleteById(Long id);
    UsersResults save(UsersResults usersResults);
    UsersResults findResult(UsersResults usersResults, UsersTests userTests);
    List<UsersResults> findResultByUser(User user);
}
