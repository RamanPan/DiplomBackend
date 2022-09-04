package ru.ramanpan.petroprimoweb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ramanpan.petroprimoweb.dto.QuestionDTO;
import ru.ramanpan.petroprimoweb.exceptions.NotFoundException;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.model.enums.DifficultyQuestion;
import ru.ramanpan.petroprimoweb.model.enums.QuestionCategory;
import ru.ramanpan.petroprimoweb.model.enums.QuestionType;
import ru.ramanpan.petroprimoweb.repository.QuestionRepo;
import ru.ramanpan.petroprimoweb.service.AnswerService;
import ru.ramanpan.petroprimoweb.service.QuestionService;
import ru.ramanpan.petroprimoweb.service.TestService;
import ru.ramanpan.petroprimoweb.util.Constants;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepo questionRepo;
    private final TestService testService;
    private final AnswerService answerService;


    @Override
    public List<Question> findAll() {
        return questionRepo.findAll();
    }

    @Override
    public Question findById(Long id) {
        return questionRepo.findById(id).orElseThrow(() -> new NotFoundException(Constants.QUESTION_NOT_FOUND));
    }

    @Override
    public List<Question> findAllByTestId(Long testId) {
        Test test = testService.findById(testId);
        return questionRepo.findAllByTest(test);
    }

    @Override
    public void deleteById(Long id) {
        Question question = findById(id);
        answerService.deleteByQuestion(question);
        questionRepo.deleteById(id);
    }

    @Override
    public List<Answer> getAnswers(Long id) {
        return answerService.findAllByQuestionId(id);
    }

    @Override
    public Question findByStatement(String statement) {
        return questionRepo.findByStatement(statement).orElseThrow(() -> new NotFoundException(Constants.QUESTION_NOT_FOUND));
    }

    @Override
    public Question save(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setStatement(questionDTO.getStatement());
        if (" ".equals(question.getPicture())) question.setPicture("plug.png");
        else question.setPicture(questionDTO.getPicture());
        question.setTest(testService.findById(questionDTO.getTestLong()));
        String questionType = questionDTO.getType();
        String questionDifficult = questionDTO.getDifficult();
        String questionCategory = questionDTO.getCategory();
        if ("Закрытый".equals(questionType)) question.setType(QuestionType.CLOSED);
        else question.setType(QuestionType.OPEN);
        if ("Легкая".equals(questionDifficult)) question.setDifficult(DifficultyQuestion.EASY);
        else if ("Средняя".equals(questionDifficult)) question.setDifficult(DifficultyQuestion.MEDIUM);
        else question.setDifficult(DifficultyQuestion.HARD);
        if ("Политическая".equals(questionCategory)) question.setCategory(QuestionCategory.POLITIC);
        else if ("Экономическая".equals(questionCategory)) question.setCategory(QuestionCategory.ECONOMIC);
        else question.setCategory(QuestionCategory.CULTURE);
        question.setCreated(new Date());
        return questionRepo.save(question);
    }
}
