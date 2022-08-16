package ru.ramanpan.petroprimoweb.service;

import ru.ramanpan.petroprimoweb.model.Answer;

import java.util.List;

public interface AnswerService {
    List<Answer> findAll();

    Answer findById(Long id);

    List<Answer> findAllByQuestionId(Long question_id);

    void deleteById(Long id);

    Answer findByStatement(String statement);

    Long save(Answer answer);
}
