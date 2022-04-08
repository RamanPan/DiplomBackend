package ru.ramanpan.petroprimoweb.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ramanpan.petroprimoweb.DTO.AnswerDTO;
import ru.ramanpan.petroprimoweb.DTO.DeleteDTO;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.service.AnswerService;
import ru.ramanpan.petroprimoweb.service.QuestionService;
import ru.ramanpan.petroprimoweb.service.impl.AnswerServiceImpl;

@RestController
@RequestMapping("/api/answers")
public class AnswerRestController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    public AnswerRestController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @DeleteMapping("/delete")
    public ResponseEntity.BodyBuilder deleteAnswer(@RequestBody DeleteDTO deleteDTO) {
        System.out.println(deleteDTO);
        answerService.deleteById(deleteDTO.getId());
        return ResponseEntity.ok();

    }

    @PostMapping("/create")
    public Long createAnswer(@RequestBody AnswerDTO answerDTO) {
        Answer answer = new Answer();
        System.out.println(answerDTO);
        answer.setIs_correct(answerDTO.getCorrectness());
        answer.setStatement(answerDTO.getStatement());
        answer.setQuestion(questionService.findById(answerDTO.getQuestion()));
        return answerService.save(answer);

    }
}
