package ru.ramanpan.petroprimoweb.service;

import ru.ramanpan.petroprimoweb.DTO.AnswerDTO;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.model.Question;

import java.util.List;

public interface AnswerService {
    List<Answer> findAll();

    Answer findById(Long id);

    List<Answer> findAllByQuestionId(Long question_id);

    void deleteById(Long id);

    Answer findByStatement(String statement);

    List<Answer> findAllByQuestionAndCorrectness(Question question, boolean correctness);

    Long save(AnswerDTO answer);
}
