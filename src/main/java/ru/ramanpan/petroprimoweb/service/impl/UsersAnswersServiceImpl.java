package ru.ramanpan.petroprimoweb.service.impl;

import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.DTO.UsersAnswersDTO;
import ru.ramanpan.petroprimoweb.model.*;
import ru.ramanpan.petroprimoweb.repository.AnswerRepo;
import ru.ramanpan.petroprimoweb.repository.QuestionRepo;
import ru.ramanpan.petroprimoweb.repository.UsersAnswersRepo;
import ru.ramanpan.petroprimoweb.service.UsersAnswersService;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class UsersAnswersServiceImpl implements UsersAnswersService {
    private final UsersAnswersRepo usersAnswersRepo;
    private final AnswerRepo answerRepo;
    private final QuestionRepo questionRepo;

    private boolean checkCorrectness(String answer, String correctAnswer) {
        if (answer.isEmpty()) return false;
        int countEquals = 0, countUnequals = 0, minLength;
        char[] ans = answer.replaceAll(" ", "").toLowerCase(Locale.ROOT).toCharArray();
        char[] corAns = correctAnswer.replaceAll(" ", "").toLowerCase(Locale.ROOT).toCharArray();
        countUnequals += Math.abs(ans.length - corAns.length);
        minLength = Math.min(ans.length, corAns.length);
        for (int i = 0; i < minLength; ++i) {
            if (ans[i] == corAns[i]) countEquals++;
            else countUnequals++;
        }

//        System.out.println(countEquals + " " + countUnequals);
        return countEquals > countUnequals;
    }

    public UsersAnswersServiceImpl(UsersAnswersRepo usersAnswersRepo, AnswerRepo answerRepo, QuestionRepo questionRepo) {
        this.usersAnswersRepo = usersAnswersRepo;
        this.answerRepo = answerRepo;
        this.questionRepo = questionRepo;
    }

    @Override
    public boolean isCorrect(UsersAnswers usersAnswers, UsersAnswersDTO answerDTO) {
        boolean correctness = false;
        Question q = questionRepo.findById(answerDTO.getQuestion()).orElse(null);
        List<Answer> right = answerRepo.findAllByQuestionAndCorrectness(q, true).orElse(null);
        assert right != null;
        assert q != null;
        if (!q.getType().toString().equals("OPEN")) {
            for (Answer r : right) {
                if (usersAnswers.getAnswer().equals(r.getStatement())) {
                    correctness = true;
                    break;
                }
            }
            return correctness;
        }
        return checkCorrectness(usersAnswers.getAnswer(), right.get(0).getStatement());
    }

    @Override
    public List<UsersAnswers> findAll() {
        return usersAnswersRepo.findAll();
    }

    @Override
    public List<UsersAnswers> findAllByUserAndTest(User user, UsersTests usersTests) {
        return usersAnswersRepo.findAllByUserAndTest(user, usersTests).orElse(null);
    }

    @Override
    public UsersAnswers findById(Long id) {
        return usersAnswersRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        usersAnswersRepo.deleteById(id);
    }

    @Override
    public UsersAnswers save(UsersAnswers usersAnswers) {
        usersAnswers.setCreated(new Date());
        return usersAnswersRepo.save(usersAnswers);
    }
}
