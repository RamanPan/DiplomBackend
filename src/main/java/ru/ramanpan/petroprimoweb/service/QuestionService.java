package ru.ramanpan.petroprimoweb.service;

import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.Test;

import java.util.List;
public interface QuestionService {
    List<Question> findAll();
    Question findById(Long id);
    List<Question> findAllByTestId(Long test_id);
    void deleteById(Long id);
    Question findByStatement(String statement);
    Long save(Question question);
}
