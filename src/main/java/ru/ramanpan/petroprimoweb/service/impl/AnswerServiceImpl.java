package ru.ramanpan.petroprimoweb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.dto.AnswerDTO;
import ru.ramanpan.petroprimoweb.exceptions.NotFoundException;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.repository.AnswerRepo;
import ru.ramanpan.petroprimoweb.service.AnswerService;
import ru.ramanpan.petroprimoweb.service.QuestionService;
import ru.ramanpan.petroprimoweb.util.Constants;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepo answerRepo;
    private final QuestionService questionService;

    @Override
    public List<Answer> findAll() {
        return answerRepo.findAll();
    }

    @Override
    public Answer findById(Long id) {
        return answerRepo.findById(id).orElseThrow(() -> new NotFoundException(Constants.ANSWER_NOT_FOUND));
    }

    @Override
    public List<Answer> findAllByQuestionId(Long questionId) {
        Question question = questionService.findById(questionId);
        return answerRepo.findAllByQuestion(question);
    }

    @Override
    public List<Answer> findAllByQuestionAndCorrectness(Question question, boolean correctness) {
        return answerRepo.findAllByQuestionAndCorrectness(question, correctness);
    }

    @Override
    public void deleteByQuestion(Question question) {
        answerRepo.deleteAllByQuestion(question);
    }

    @Override
    public void deleteById(Long id) {
        answerRepo.deleteById(id);
    }

    @Override
    public Answer findByStatement(String statement) {
        return answerRepo.findByStatement(statement).orElseThrow(() -> new NotFoundException(Constants.ANSWER_NOT_FOUND));
    }

    @Override
    public Answer save(AnswerDTO answerDTO) {
        Answer answer = new Answer();
        answer.setCorrectness(answerDTO.getCorrectness());
        answer.setStatement(answerDTO.getStatement());
        answer.setQuestion(questionService.findById(answerDTO.getQuestionLong()));
        answer.setCreated(new Date());
        return answerRepo.save(answer);
    }
}
