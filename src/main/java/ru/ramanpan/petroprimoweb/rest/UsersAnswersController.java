package ru.ramanpan.petroprimoweb.rest;


import org.springframework.web.bind.annotation.*;
import ru.ramanpan.petroprimoweb.DTO.*;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.UsersAnswers;
import ru.ramanpan.petroprimoweb.model.UsersResults;
import ru.ramanpan.petroprimoweb.repository.AnswerRepo;
import ru.ramanpan.petroprimoweb.service.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/usersAnswers")
public class UsersAnswersController {
    private final UsersAnswersService usersAnswersService;
    private final UserService userService;
    private final QuestionService questionService;
    private final UsersTestsService usersTestsService;
    private final AnswerRepo answerService;

    private UserAnswerDTO toUserAnswerDTO(UsersAnswers usersAnswers) {
        Question q =questionService.findById(usersAnswers.getQuestion().getId());
        List<Answer> answers = answerService.findAllByQuestionAndCorrectness(q,true).orElse(null);
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

    public UsersAnswersController(UsersAnswersService usersAnswersService, UserService userService, QuestionService questionService, UsersTestsService usersTestsService, AnswerRepo answerService) {
        this.usersAnswersService = usersAnswersService;
        this.userService = userService;
        this.questionService = questionService;
        this.usersTestsService = usersTestsService;
        this.answerService = answerService;
    }

    @PostMapping("/create")
    public Long createUsersAnswer(@RequestBody UsersAnswersDTO answerDTO) {
        UsersAnswers answer = new UsersAnswers();
        System.out.println(answerDTO);
        answer.setAnswer(answerDTO.getAnswer());
        answer.setTest(usersTestsService.findById(answerDTO.getUserTest()));
        answer.setUser(userService.findById(answerDTO.getUser()));
        answer.setQuestion(questionService.findById(answerDTO.getQuestion()));
        answer.setCorrect(usersAnswersService.isCorrect(answer,answerDTO));
        return usersAnswersService.save(answer).getId();

    }
    @PostMapping("/getUserAnswers")
    public List<UserAnswerDTO> getUserAnswers(@RequestBody UsersAnswersDTO dto ) {
        List<UserAnswerDTO> answerDTOS = new ArrayList<>();
        List<UsersAnswers> usersAnswers = usersAnswersService.findAllByUserAndTest(userService.findById(dto.getUser()),usersTestsService.findById(dto.getUserTest()));
        for(UsersAnswers u : usersAnswers) answerDTOS.add(toUserAnswerDTO(u));
        return answerDTOS;
    }


    @DeleteMapping("/delete")
    public Integer deleteUsersAnswer(@RequestBody DeleteDTO deleteDTO) {
        System.out.println(deleteDTO);
        usersAnswersService.deleteById(deleteDTO.getId());
        return 1;

    }


}
