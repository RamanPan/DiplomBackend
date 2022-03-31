package ru.ramanpan.petroprimoweb.service;


import ru.ramanpan.petroprimoweb.model.Test;

import java.util.List;

public interface TestService {
    List<Test> findAll();
    Test findById(Long id);
    void deleteById(Long id);
    Test findByName(String name);
    void save(Test test);
}