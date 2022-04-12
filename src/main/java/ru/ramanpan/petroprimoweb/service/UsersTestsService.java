package ru.ramanpan.petroprimoweb.service;

import ru.ramanpan.petroprimoweb.DTO.UsersResultsDTO;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.model.UsersAnswers;
import ru.ramanpan.petroprimoweb.model.UsersResults;
import ru.ramanpan.petroprimoweb.model.UsersTests;

import java.util.List;

public interface UsersTestsService {
    List<UsersTests> findAll();
    UsersTests findById(Long id);
    void deleteById(Long id);
    UsersTests save(UsersTests usersTests);
    Long update(UsersTests usersTests, UsersResultsDTO dto);
}
