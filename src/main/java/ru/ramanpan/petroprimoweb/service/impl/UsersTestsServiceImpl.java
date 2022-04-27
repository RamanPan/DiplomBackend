package ru.ramanpan.petroprimoweb.service.impl;

import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.DTO.UsersResultsDTO;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.model.UsersResults;
import ru.ramanpan.petroprimoweb.model.UsersTests;
import ru.ramanpan.petroprimoweb.model.enums.Correctness;
import ru.ramanpan.petroprimoweb.repository.TestRepo;
import ru.ramanpan.petroprimoweb.repository.UserRepo;
import ru.ramanpan.petroprimoweb.repository.UsersResultsRepo;
import ru.ramanpan.petroprimoweb.repository.UsersTestsRepo;
import ru.ramanpan.petroprimoweb.service.UsersResultsService;
import ru.ramanpan.petroprimoweb.service.UsersTestsService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UsersTestsServiceImpl implements UsersTestsService{
    private final UsersTestsRepo usersTestsRepo;
    private final UserRepo userRepo;
    private final TestRepo testRepo;
    private final UsersResultsService usersResultsService;

    public UsersTestsServiceImpl(UsersTestsRepo usersTestsRepo, UserRepo userRepo, TestRepo testRepo, UsersResultsService usersResultsService) {
        this.usersTestsRepo = usersTestsRepo;
        this.userRepo = userRepo;
        this.testRepo = testRepo;
        this.usersResultsService = usersResultsService;
    }

    @Override
    public List<UsersTests> findAll() {
        return usersTestsRepo.findAll();
    }

    @Override
    public UsersTests findById(Long id) {
        return usersTestsRepo.findById(id).orElse(null);
    }
    @Override
    public void deleteById(Long id) {
        usersTestsRepo.deleteById(id);
    }

    @Override
    public UsersTests setMark(UsersTests usersTests) {
        Test t =usersTests.getTest();
        t.setNumberPasses(t.getNumberPasses()+1);
        List<UsersTests> usersTestsList = usersTestsRepo.findAllByTest(t).orElse(null);
        assert usersTestsList != null;
        double markTest = usersTests.getMark(); int counter = 1;
        for(UsersTests u : usersTestsList) {
            if(u.getMark() != null && u.getMark() != 0) {markTest += u.getMark();++counter;}
        }
        t.setMark(markTest/counter);
        testRepo.save(t);
        return usersTestsRepo.save(usersTests);
    }

    @Override
    public UsersResults setResultToTest(UsersTests usersTests, UsersResultsDTO dto) {
        UsersResults usersResults = new UsersResults();
        Test t = testRepo.findById(usersTests.getTest().getId()).orElse(null);
        User u = userRepo.findById(usersTests.getUser().getId()).orElse(null);
        assert t != null;
        t.setNumberPasses(t.getNumberPasses()+1);
        assert u != null;
        u.setCountPassed(u.getCountPassed()+1);
        usersResults.setCreated(new Date());
        usersTests.setCorrectness(Correctness.INCORRECT);
        usersResults.setUser(userRepo.findById(dto.getUser()).orElse(null));
        usersResultsService.findResult(usersResults,usersTests);
        usersResults.setTest(usersTests);
        if(usersTests.getCorrectness().equals(Correctness.CORRECT)) u.setCountPassedCorrect(u.getCountPassedCorrect() + 1);
        else u.setCountPassedIncorrect(u.getCountPassedIncorrect() + 1);
        userRepo.save(u);
        usersTestsRepo.save(usersTests);
        return usersResultsService.save(usersResults);
    }

    @Override
    public UsersTests save(UsersTests usersTests) {
        usersTests.setCreated(new Date());
        usersTests.setCorrectness(Correctness.PASSING);
        return usersTestsRepo.save(usersTests);
    }
}
