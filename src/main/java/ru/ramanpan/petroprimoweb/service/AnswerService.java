package ru.ramanpan.petroprimoweb.service;

import ru.ramanpan.petroprimoweb.dto.AnswerDTO;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.model.Question;

import java.util.List;

public interface AnswerService {
    List<Answer> findAll();

    Answer findById(Long id);

    List<Answer> findAllByQuestionId(Long questionId);

    void deleteById(Long id);

    Answer findByStatement(String statement);

    void deleteByQuestion(Question question);

    List<Answer> findAllByQuestionAndCorrectness(Question question, boolean correctness);

    Answer save(AnswerDTO answerDTO);
}
