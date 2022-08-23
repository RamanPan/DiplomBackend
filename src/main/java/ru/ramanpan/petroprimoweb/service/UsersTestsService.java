package ru.ramanpan.petroprimoweb.service;

import ru.ramanpan.petroprimoweb.dto.UsersResultsDTO;
import ru.ramanpan.petroprimoweb.model.UsersResults;
import ru.ramanpan.petroprimoweb.model.UsersTests;

import java.util.List;

public interface UsersTestsService {
    List<UsersTests> findAll();

    UsersTests findById(Long id);

    void deleteById(Long id);

    UsersTests save(UsersTests usersTests);

    UsersTests setMark(UsersTests usersTests);

    UsersResults setResultToTest(UsersTests usersTests, UsersResultsDTO dto);
}
