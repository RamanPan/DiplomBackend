package ru.ramanpan.petroprimoweb.service.impl;

import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.model.*;
import ru.ramanpan.petroprimoweb.model.enums.Correctness;
import ru.ramanpan.petroprimoweb.repository.*;
import ru.ramanpan.petroprimoweb.service.UsersResultsService;

import java.util.Date;
import java.util.List;
@Service
public class UsersResultsServiceImpl implements UsersResultsService {
    private final UsersResultsRepo usersResultsRepo;
    private final UserRepo userRepo;
    private final ResultRepo resultRepo;
    private final UsersAnswersRepo usersAnswersRepo;

    public UsersResultsServiceImpl(UsersResultsRepo usersResultsRepo, UserRepo userRepo, ResultRepo resultRepo, UsersAnswersRepo usersAnswersRepo) {
        this.usersResultsRepo = usersResultsRepo;
        this.userRepo = userRepo;
        this.resultRepo = resultRepo;
        this.usersAnswersRepo = usersAnswersRepo;
    }

    @Override
    public UsersResults findResult(UsersResults usersResults, UsersTests userTests) {
        double countRight = 0.0; double result;
        List<UsersAnswers> usersAnswers = usersAnswersRepo.findAllByTest(userTests).orElse(null);
        List<Result> results = resultRepo.findAllByTest(userTests.getTest()).orElse(null);
        System.out.println(usersAnswers);
        assert usersAnswers != null;
        for(UsersAnswers answer : usersAnswers) {
            if(answer.getCorrect()) ++countRight;
        }
        result = countRight/usersAnswers.size() * 100;
        assert results != null;
        for (Result r : results) {
            if(r.getStartCondition() <= result && result < r.getEndCondition()) {
                usersResults.setResult(r);
                usersResults.setResultNum(result);
                if(r.getCorrectness()) userTests.setCorrectness(Correctness.CORRECT);
            }
        }
        return usersResults;
    }

    @Override
    public List<UsersResults> findResultByUser(User user) {
        return usersResultsRepo.findAllByUser(user).orElse(null);
    }

    @Override
    public List<UsersResults> findAll() {
        return usersResultsRepo.findAll();
    }

    @Override
    public UsersResults findById(Long id) {
        return usersResultsRepo.findById(id).orElse(null);
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
