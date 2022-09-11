package ru.ramanpan.petroprimoweb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.dto.UsersAnswersDTO;
import ru.ramanpan.petroprimoweb.exceptions.NotFoundException;
import ru.ramanpan.petroprimoweb.model.*;
import ru.ramanpan.petroprimoweb.repository.UsersAnswersRepo;
import ru.ramanpan.petroprimoweb.service.*;
import ru.ramanpan.petroprimoweb.util.Constants;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class UsersAnswersServiceImpl implements UsersAnswersService {
    private final UsersAnswersRepo usersAnswersRepo;
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final UsersTestsService usersTestsService;
    private final UserService userService;


    private boolean checkCorrectness(String answer, String correctAnswer) {
        if (answer.isEmpty()) return false;
        int countEquals = 0;
        int countUnequals = 0;
        int minLength;
        char[] ans = answer.replace(" ", "").toLowerCase(Locale.ROOT).toCharArray();
        char[] corAns = correctAnswer.replace(" ", "").toLowerCase(Locale.ROOT).toCharArray();
        countUnequals += Math.abs(ans.length - corAns.length);
        minLength = Math.min(ans.length, corAns.length);
        for (int i = 0; i < minLength; ++i) {
            if (ans[i] == corAns[i]) countEquals++;
            else countUnequals++;
        }
        return countEquals > countUnequals;
    }

    @Override
    public boolean isCorrect(UsersAnswers usersAnswers, UsersAnswersDTO answerDTO) {
        boolean correctness = false;
        Question q = questionService.findById(answerDTO.getQuestion());
        if(q == null) return false;
        List<Answer> right = answerService.findAllByQuestionAndCorrectness(q, true);
        if (!"OPEN".equals(q.getType().toString())) {
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
        return usersAnswersRepo.findAllByUserAndTest(user, usersTests);
    }

    @Override
    public UsersAnswers findById(Long id) {
        return usersAnswersRepo.findById(id).orElseThrow(() -> new NotFoundException(Constants.USER_ANSWER_NOT_FOUND));
    }

    @Override
    public void deleteById(Long id) {
        usersAnswersRepo.deleteById(id);
    }

    @Override
    public UsersAnswers save(UsersAnswersDTO usersAnswersDTO) {
        UsersAnswers answer = new UsersAnswers();
        answer.setAnswer(usersAnswersDTO.getAnswer());
        answer.setTest(usersTestsService.findById(usersAnswersDTO.getUserTest()));
        answer.setUser(userService.findById(usersAnswersDTO.getUser()));
        answer.setQuestion(questionService.findById(usersAnswersDTO.getQuestion()));
        answer.setCorrect(isCorrect(answer, usersAnswersDTO));
        answer.setCreated(new Date());
        return usersAnswersRepo.save(answer);
    }
}
