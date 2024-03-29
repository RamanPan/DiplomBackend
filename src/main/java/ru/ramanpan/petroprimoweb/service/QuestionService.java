package ru.ramanpan.petroprimoweb.service;

import ru.ramanpan.petroprimoweb.dto.QuestionDTO;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.model.Question;

import java.util.List;

public interface QuestionService {
    List<Question> findAll();

    Question findById(Long id);

    List<Question> findAllByTestId(Long testId);

    void deleteById(Long id);

    List<Answer> getAnswers(Long id);

    Question findByStatement(String statement);

    Question save(QuestionDTO questionDTO);
}
