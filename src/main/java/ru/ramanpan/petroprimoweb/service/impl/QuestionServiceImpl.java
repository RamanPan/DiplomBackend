package ru.ramanpan.petroprimoweb.service.impl;

import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.repository.QuestionRepo;
import ru.ramanpan.petroprimoweb.repository.TestRepo;
import ru.ramanpan.petroprimoweb.service.QuestionService;

import java.util.Date;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepo questionRepo;
    private final TestRepo testRepo;

    public QuestionServiceImpl(QuestionRepo questionRepo,TestRepo testRepo) {

        this.questionRepo = questionRepo;
        this.testRepo = testRepo;
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
    public void deleteById(Long id) {
        questionRepo.deleteById(id);
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
