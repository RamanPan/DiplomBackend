package ru.ramanpan.petroprimoweb.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ramanpan.petroprimoweb.DTO.*;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.UsersAnswers;
import ru.ramanpan.petroprimoweb.repository.AnswerRepo;
import ru.ramanpan.petroprimoweb.service.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/usersAnswers")
@AllArgsConstructor
public class UsersAnswersController {
    private final UsersAnswersService usersAnswersService;
    private final UserService userService;
    private final QuestionService questionService;
    private final UsersTestsService usersTestsService;
    private final AnswerService answerService;

    private UserAnswerDTO toUserAnswerDTO(UsersAnswers usersAnswers) {
        Question q = questionService.findById(usersAnswers.getQuestion().getId());
        List<Answer> answers = answerService.findAllByQuestionAndCorrectness(q, true);
        List<String> textAnswers = new ArrayList<>();
        assert answers != null;
        for (Answer answer : answers) textAnswers.add(answer.getStatement());
        UserAnswerDTO u = new UserAnswerDTO();
        u.setId(usersAnswers.getId());
        u.setAnswer(usersAnswers.getAnswer());
        u.setCorrectness(usersAnswers.getCorrect());
        u.setPicture(q.getPicture());
        u.setRightAnswer(textAnswers);
        u.setStatement(q.getStatement());
        return u;
    }


    @PostMapping("/create")
    public ResponseEntity<Long> createUsersAnswer(@RequestBody UsersAnswersDTO usersAnswersDTO) {
        return ResponseEntity.ok(usersAnswersService.save(usersAnswersDTO).getId());

    }

    @PostMapping("/getUserAnswers")
    public ResponseEntity<List<UserAnswerDTO>> getUserAnswers(@RequestBody UsersAnswersDTO dto) {
        List<UserAnswerDTO> answerDTOS = new ArrayList<>();
        List<UsersAnswers> usersAnswers = usersAnswersService.findAllByUserAndTest(userService.findById(dto.getUser()), usersTestsService.findById(dto.getUserTest()));
        for (UsersAnswers u : usersAnswers) answerDTOS.add(toUserAnswerDTO(u));
        return ResponseEntity.ok(answerDTOS);
    }


    @DeleteMapping("/delete")
    public ResponseEntity.BodyBuilder deleteUsersAnswer(@RequestBody DeleteDTO deleteDTO) {
        usersAnswersService.deleteById(deleteDTO.getId());
        return ResponseEntity.ok();

    }


}
