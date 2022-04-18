package ru.ramanpan.petroprimoweb.service.impl;

import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.DTO.AnswerDTO;
import ru.ramanpan.petroprimoweb.DTO.UsersAnswersDTO;
import ru.ramanpan.petroprimoweb.model.*;
import ru.ramanpan.petroprimoweb.repository.*;
import ru.ramanpan.petroprimoweb.service.UsersAnswersService;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UsersAnswersServiceImpl implements UsersAnswersService {
    private final UsersAnswersRepo usersAnswersRepo;
    private final AnswerRepo answerRepo;
    private final QuestionRepo questionRepo;

    public UsersAnswersServiceImpl(UsersAnswersRepo usersAnswersRepo, AnswerRepo answerRepo, QuestionRepo questionRepo) {
        this.usersAnswersRepo = usersAnswersRepo;
        this.answerRepo = answerRepo;
        this.questionRepo = questionRepo;
    }

    @Override
    public boolean isCorrect(UsersAnswers usersAnswers, UsersAnswersDTO answerDTO) {
        boolean correctness = false;
        Question q = questionRepo.findById(answerDTO.getQuestion()).orElse(null);
        List<Answer> right = answerRepo.findAllByQuestionAndCorrectness(q,true).orElse(null);
        System.out.println(right);
        assert right != null;
        for (Answer r: right) {
            if (usersAnswers.getAnswer().equals(r.getStatement())) {
                correctness = true;
                break;
            }
        }
        return correctness;
    }

    @Override
    public List<UsersAnswers> findAll() {
        return usersAnswersRepo.findAll();
    }

    @Override
    public List<UsersAnswers> findAllByUserAndTest(User user, UsersTests usersTests) {
        return usersAnswersRepo.findAllByUserAndTest(user,usersTests).orElse(null);
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
