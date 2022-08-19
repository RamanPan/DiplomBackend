package ru.ramanpan.petroprimoweb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ramanpan.petroprimoweb.DTO.AnswerDTO;
import ru.ramanpan.petroprimoweb.exceptions.NotFoundException;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.repository.AnswerRepo;
import ru.ramanpan.petroprimoweb.repository.QuestionRepo;
import ru.ramanpan.petroprimoweb.service.AnswerService;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepo answerRepo;
    private final QuestionRepo questionRepo;


    @Override
    public List<Answer> findAll() {
        return answerRepo.findAll();
    }

    @Override
    public Answer findById(Long id) {
        return answerRepo.findById(id).orElseThrow(() -> new NotFoundException("Answer not found"));
    }

    @Override
    public List<Answer> findAllByQuestionId(Long question_id) {
        Question question = questionRepo.getById(question_id);
        return answerRepo.findAllByQuestion(question).orElseThrow(() -> new NotFoundException("Answers not found"));
    }

    @Override
    public List<Answer> findAllByQuestionAndCorrectness(Question question, boolean correctness) {
        return answerRepo.findAllByQuestionAndCorrectness(question, correctness).orElseThrow(() -> new NotFoundException("Answers not found"));
    }

    @Override
    public void deleteById(Long id) {
        answerRepo.delete(findById(id));
    }

    @Override
    public Answer findByStatement(String statement) {
        return answerRepo.findByStatement(statement).orElseThrow(() -> new NotFoundException("Answer not found"));
    }

    @Override
    public Long save(AnswerDTO answerDTO) {
        Answer answer = new Answer();
        answer.setCorrectness(answerDTO.getCorrectness());
        answer.setStatement(answerDTO.getStatement());
        answer.setQuestion(questionRepo.findById(answerDTO.getQuestionLong()).orElseThrow(() -> new NotFoundException("Answer not found")));
        answer.setCreated(new Date());
        return answerRepo.save(answer).getId();
    }
}
