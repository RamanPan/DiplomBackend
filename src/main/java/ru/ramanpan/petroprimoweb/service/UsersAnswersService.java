package ru.ramanpan.petroprimoweb.service;

import ru.ramanpan.petroprimoweb.DTO.UsersAnswersDTO;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.model.UsersAnswers;
import ru.ramanpan.petroprimoweb.model.UsersTests;

import java.util.List;

public interface UsersAnswersService {
    List<UsersAnswers> findAll();

    UsersAnswers findById(Long id);

    void deleteById(Long id);

    boolean isCorrect(UsersAnswers usersAnswers, UsersAnswersDTO answerDTO);

    UsersAnswers save(UsersAnswersDTO usersAnswersDTO);

    List<UsersAnswers> findAllByUserAndTest(User user, UsersTests usersTests);
}
