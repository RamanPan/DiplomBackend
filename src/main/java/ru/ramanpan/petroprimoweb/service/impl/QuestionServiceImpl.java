package ru.ramanpan.petroprimoweb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ramanpan.petroprimoweb.DTO.QuestionDTO;
import ru.ramanpan.petroprimoweb.exceptions.NotFoundException;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.model.enums.DifficultyQuestion;
import ru.ramanpan.petroprimoweb.model.enums.QuestionCategory;
import ru.ramanpan.petroprimoweb.model.enums.QuestionType;
import ru.ramanpan.petroprimoweb.repository.AnswerRepo;
import ru.ramanpan.petroprimoweb.repository.QuestionRepo;
import ru.ramanpan.petroprimoweb.repository.TestRepo;
import ru.ramanpan.petroprimoweb.service.QuestionService;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepo questionRepo;
    private final TestRepo testRepo;
    private final AnswerRepo answerRepo;


    @Override
    public List<Question> findAll() {
        return questionRepo.findAll();
    }

    @Override
    public Question findById(Long id) {
        return questionRepo.findById(id).orElseThrow(() -> new NotFoundException("Question not found"));
    }

    @Override
    public List<Question> findAllByTestId(Long test_id) {
        Test test = testRepo.getById(test_id);
        return questionRepo.findAllByTest(test).orElseThrow(() -> new NotFoundException("Questions not found"));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Question question = findById(id);
        answerRepo.deleteAllByQuestion(question);
        questionRepo.deleteById(id);
    }

    @Override
    public List<Answer> getAnswers(Long id) {
        Question question = findById(id);
        return answerRepo.findAllByQuestion(question).orElseThrow(() -> new NotFoundException("Answers not found"));
    }

    @Override
    public Question findByStatement(String statement) {
        return questionRepo.findByStatement(statement).orElseThrow(() -> new NotFoundException("Question not found"));
    }

    @Override
    public Long save(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setStatement(questionDTO.getStatement());
        if (questionDTO.getPicture().equals(" ")) question.setPicture("plug.png");
        else question.setPicture(questionDTO.getPicture());
        question.setTest(testRepo.findById(questionDTO.getTestLong()).orElseThrow(() -> new NotFoundException("Test not found")));
        String questionType = questionDTO.getType();
        String questionDifficult = questionDTO.getDifficult();
        String questionCategory = questionDTO.getCategory();
        if (questionType.equals("Закрытый")) question.setType(QuestionType.CLOSED);
        else question.setType(QuestionType.OPEN);
        if (questionDifficult.equals("Легкая")) question.setDifficult(DifficultyQuestion.EASY);
        else if (questionDifficult.equals("Средняя")) question.setDifficult(DifficultyQuestion.MEDIUM);
        else question.setDifficult(DifficultyQuestion.HARD);
        if (questionCategory.equals("Политическая")) question.setCategory(QuestionCategory.POLITIC);
        else if (questionCategory.equals("Экономическая")) question.setCategory(QuestionCategory.ECONOMIC);
        else question.setCategory(QuestionCategory.CULTURE);
        question.setCreated(new Date());
        return questionRepo.save(question).getId();
    }
}
