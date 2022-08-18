package ru.ramanpan.petroprimoweb.service.impl;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.DTO.TestDTO;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.model.enums.Status;
import ru.ramanpan.petroprimoweb.model.enums.TestType;
import ru.ramanpan.petroprimoweb.repository.TestRepo;
import ru.ramanpan.petroprimoweb.service.TestService;
import ru.ramanpan.petroprimoweb.service.UserService;
import ru.ramanpan.petroprimoweb.util.Switches;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepo testRepo;
    private final UserService userService;

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
        Test t = testRepo.findById(id).orElse(null);
        System.out.println(t);
        assert t != null;
        t.setStatus(Status.DELETED);
        testRepo.save(t);
    }

    @Override
    public List<Test> findByAuthor(String author) {
        return testRepo.findAllByAuthor(author).orElse(null);
    }

    @Override
    public Test update(TestDTO testDTO) {
        Test test = testRepo.findById(testDTO.getId()).orElse(null);
        assert test != null;
        test.setName(testDTO.getName());
        test.setAuthor(testDTO.getAuthor());
        test.setDescription(testDTO.getDescription());
        if (testDTO.getPicture().equals("")) test.setPicture("plug.png");
        else test.setPicture(testDTO.getPicture());
        String testType = testDTO.getTestType();
        test.setTestType(Switches.selectionTestType(testType));
        if (test.getTestType().equals(TestType.DETERMINISTIC)) {
            String option = testDTO.getOptionForDeterministicType();
            test.setOptionForDeterministicType(Switches.selectionOption(option));
        }
        return testRepo.save(test);
    }

    @Override
    public Test specificUpdate(Test test) {
        return testRepo.save(test);
    }

    @Override
    public Test findByName(String name) {
        return testRepo.findByName(name).orElse(null);
    }

    @Override
    public Test save(TestDTO testDTO) {
        Test test = new Test();
        test.setName(testDTO.getName());
        test.setAuthor(testDTO.getAuthor());
        test.setDescription(testDTO.getDescription());
        test.setMark(0.0);
        test.setNumberPasses(0);
        test.setNumberQuestions(0);
        User u = userService.findByNickname(testDTO.getAuthor());
        u.setCountCreated(u.getCountCreated() + 1);
        userService.update(u);
        if (testDTO.getPicture().equals("")) test.setPicture("plug.png");
        else test.setPicture(testDTO.getPicture());
        String testType = testDTO.getTestType();
        test.setTestType(Switches.selectionTestType(testType));
        if (test.getTestType().equals(TestType.DETERMINISTIC)) {
            String option = testDTO.getOptionForDeterministicType();
            test.setOptionForDeterministicType(Switches.selectionOption(option));
        }
        test.setCreated(new Date());
        test.setStatus(Status.ACTIVE);
        test.setPercentCulture(-1);
        test.setPercentPolitic(-1);
        test.setPercentEconomic(-1);
        return testRepo.save(test);
    }

}
