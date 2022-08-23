package ru.ramanpan.petroprimoweb.service;


import ru.ramanpan.petroprimoweb.dto.TestDTO;
import ru.ramanpan.petroprimoweb.model.Test;

import java.util.List;

public interface TestService {
    List<Test> findAll();

    Test findById(Long id);

    void deleteById(Long id);

    Test findByName(String name);

    Test update(TestDTO testDTO);

    Test specificUpdate(Test test);

    Test save(TestDTO testDTO);

    List<Test> findByAuthor(String author);
}
