package ru.ramanpan.petroprimoweb.service.impl;

import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.repository.AnswerRepo;
import ru.ramanpan.petroprimoweb.repository.QuestionRepo;
import ru.ramanpan.petroprimoweb.service.AnswerService;

import java.util.Date;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepo answerRepo;
    private final QuestionRepo questionRepo;

    public AnswerServiceImpl(AnswerRepo answerRepo, QuestionRepo questionRepo) {
        this.answerRepo = answerRepo;
        this.questionRepo = questionRepo;
    }

    @Override
    public List<Answer> findAll() {
        return answerRepo.findAll();
    }

    @Override
    public Answer findById(Long id) {
        return answerRepo.findById(id).orElse(null);
    }

    @Override
    public List<Answer> findAllByQuestionId(Long question_id) {
        Question question = questionRepo.getById(question_id);
        return answerRepo.findAllByQuestion(question).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        answerRepo.deleteById(id);
    }

    @Override
    public Answer findByStatement(String statement) {
        return answerRepo.findByStatement(statement).orElse(null);
    }

    @Override
    public Long save(Answer answer) {
        answer.setCreated(new Date());
        return answerRepo.save(answer).getId();
    }
}
