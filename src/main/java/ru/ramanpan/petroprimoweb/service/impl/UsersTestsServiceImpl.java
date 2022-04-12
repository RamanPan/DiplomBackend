package ru.ramanpan.petroprimoweb.service.impl;

import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.DTO.UsersResultsDTO;
import ru.ramanpan.petroprimoweb.model.Test;
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
    public Long update(UsersTests usersTests, UsersResultsDTO dto) {
        UsersResults usersResults = new UsersResults();
        usersResults.setCreated(new Date());
        usersTests.setCorrectness(Correctness.INCORRECT);
        usersResults.setUser(userRepo.findById(dto.getUser()).orElse(null));
        usersResultsService.findResult(usersResults,usersTests);
        System.out.println(usersResults);
        usersResults.setTest(usersTests);
        usersResultsService.save(usersResults);
        return usersTestsRepo.save(usersTests).getId();
    }

    @Override
    public UsersTests save(UsersTests usersTests) {
        usersTests.setCreated(new Date());
        usersTests.setCorrectness(Correctness.PASSING);
        return usersTestsRepo.save(usersTests);
    }
}
