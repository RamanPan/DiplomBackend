package ru.ramanpan.petroprimoweb.service;

import ru.ramanpan.petroprimoweb.DTO.AnswerDTO;
import ru.ramanpan.petroprimoweb.DTO.UsersAnswersDTO;
import ru.ramanpan.petroprimoweb.DTO.UsersTestsDTO;
import ru.ramanpan.petroprimoweb.model.*;

import java.util.List;

public interface UsersAnswersService {
    List<UsersAnswers> findAll();
    UsersAnswers findById(Long id);
    void deleteById(Long id);
    boolean isCorrect(UsersAnswers usersAnswers, UsersAnswersDTO answerDTO);
    UsersAnswers save(UsersAnswers usersAnswers);
    List<UsersAnswers> findAllByUserAndTest(User user, UsersTests usersTests);
}
