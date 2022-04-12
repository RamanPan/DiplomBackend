package ru.ramanpan.petroprimoweb.service;

import ru.ramanpan.petroprimoweb.DTO.AnswerDTO;
import ru.ramanpan.petroprimoweb.DTO.UsersAnswersDTO;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.model.UsersAnswers;
import ru.ramanpan.petroprimoweb.model.UsersResults;

import java.util.List;

public interface UsersAnswersService {
    List<UsersAnswers> findAll();
    UsersAnswers findById(Long id);
    void deleteById(Long id);
    boolean isCorrect(UsersAnswers usersAnswers, UsersAnswersDTO answerDTO);
    UsersAnswers save(UsersAnswers usersAnswers);
}
