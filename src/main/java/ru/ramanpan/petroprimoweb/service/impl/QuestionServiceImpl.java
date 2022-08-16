package ru.ramanpan.petroprimoweb.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.repository.AnswerRepo;
import ru.ramanpan.petroprimoweb.repository.QuestionRepo;
import ru.ramanpan.petroprimoweb.repository.TestRepo;
import ru.ramanpan.petroprimoweb.service.QuestionService;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepo questionRepo;
    private final TestRepo testRepo;
    private final AnswerRepo answerRepo;

    public QuestionServiceImpl(QuestionRepo questionRepo, TestRepo testRepo, AnswerRepo answerRepo) {

        this.questionRepo = questionRepo;
        this.testRepo = testRepo;
        this.answerRepo = answerRepo;
    }

    @Override
    public List<Question> findAll() {
        return questionRepo.findAll();
    }

    @Override
    public Question findById(Long id) {
        return questionRepo.findById(id).orElse(null);
    }

    @Override
    public List<Question> findAllByTestId(Long test_id) {
        Test test = testRepo.getById(test_id);
        return questionRepo.findAllByTest(test).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Question question = findById(id);
        System.out.println("here");
        answerRepo.deleteAllByQuestion(question);
        questionRepo.deleteById(id);
    }

    @Override
    public List<Answer> getAnswers(Long id) {
        Question question = findById(id);
        return answerRepo.findAllByQuestion(question).orElse(null);
    }

    @Override
    public Question findByStatement(String statement) {
        return questionRepo.findByStatement(statement).orElse(null);
    }

    @Override
    public Long save(Question question) {
        question.setCreated(new Date());
        return questionRepo.save(question).getId();
    }
}
