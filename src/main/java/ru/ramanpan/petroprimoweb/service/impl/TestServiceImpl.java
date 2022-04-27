package ru.ramanpan.petroprimoweb.service.impl;


import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.repository.TestRepo;
import ru.ramanpan.petroprimoweb.service.TestService;

import java.util.Date;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepo testRepo;

    public TestServiceImpl(TestRepo testRepo) {
        this.testRepo = testRepo;
    }

    @Override
    public List<Test> findAll() {
        return testRepo.findAll();
    }

    @Override
    public Test findById(Long id) {
        return testRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        testRepo.deleteById(id);
    }

    @Override
    public List<Test> findByAuthor(String author) {
        return testRepo.findAllByAuthor(author).orElse(null);
    }

    @Override
    public Test update(Test test) {
        return testRepo.save(test);
    }

    @Override
    public Test findByName(String name) {
        return testRepo.findByName(name).orElse(null);
    }

    @Override
    public Test save(Test test) {
        test.setCreated(new Date());
        test.setPercentCulture(-1);
        test.setPercentPolitic(-1);
        test.setPercentEconomic(-1);
        return testRepo.save(test);
    }
}
