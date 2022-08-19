package ru.ramanpan.petroprimoweb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.exceptions.NotFoundException;
import ru.ramanpan.petroprimoweb.model.*;
import ru.ramanpan.petroprimoweb.model.enums.Correctness;
import ru.ramanpan.petroprimoweb.repository.ResultRepo;
import ru.ramanpan.petroprimoweb.repository.UsersAnswersRepo;
import ru.ramanpan.petroprimoweb.repository.UsersResultsRepo;
import ru.ramanpan.petroprimoweb.service.UsersResultsService;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class UsersResultsServiceImpl implements UsersResultsService {
    private final UsersResultsRepo usersResultsRepo;
    private final ResultRepo resultRepo;
    private final UsersAnswersRepo usersAnswersRepo;

    @Override
    public UsersResults findResult(UsersResults usersResults, UsersTests userTests) {
        double countRight = 0.0;
        double result;
        List<UsersAnswers> usersAnswers = usersAnswersRepo.findAllByTest(userTests).orElseThrow(() -> new NotFoundException("User answer was not found"));
        List<Result> results = resultRepo.findAllByTest(userTests.getTest()).orElseThrow(() -> new NotFoundException("Result was not found"));
        for (UsersAnswers answer : usersAnswers) {
            if (answer.getCorrect()) ++countRight;
        }
        result = countRight / usersAnswers.size() * 100;
        for (Result r : results) {
            if (r.getStartCondition() <= result && result <= r.getEndCondition()) {
                usersResults.setResult(r);
                usersResults.setResultNum(result);
                if (r.getCorrectness()) userTests.setCorrectness(Correctness.CORRECT);
            }
        }
        return usersResults;
    }

    @Override
    public List<UsersResults> findResultByUser(User user) {
        return usersResultsRepo.findAllByUser(user).orElseThrow(() -> new NotFoundException("User results were not found"));
    }

    @Override
    public UsersResults findResultByUserAndTest(User user, UsersTests usersTests) {
        return usersResultsRepo.findAllByUserAndTest(user, usersTests).orElseThrow(() -> new NotFoundException("User result was not found"));
    }

    @Override
    public List<UsersResults> findAll() {
        return usersResultsRepo.findAll();
    }

    @Override
    public UsersResults findById(Long id) {
        return usersResultsRepo.findById(id).orElseThrow(() -> new NotFoundException("User result was not found"));
    }

    @Override
    public void deleteById(Long id) {
        usersResultsRepo.deleteById(id);
    }

    @Override
    public UsersResults save(UsersResults usersResults) {
        usersResults.setCreated(new Date());
        return usersResultsRepo.save(usersResults);
    }
}
