package ru.ramanpan.petroprimoweb.rest;


import org.springframework.web.bind.annotation.*;
import ru.ramanpan.petroprimoweb.DTO.AnswerDTO;
import ru.ramanpan.petroprimoweb.DTO.DeleteDTO;
import ru.ramanpan.petroprimoweb.DTO.UsersAnswersDTO;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.UsersAnswers;
import ru.ramanpan.petroprimoweb.service.QuestionService;
import ru.ramanpan.petroprimoweb.service.UserService;
import ru.ramanpan.petroprimoweb.service.UsersAnswersService;
import ru.ramanpan.petroprimoweb.service.UsersTestsService;

@RestController
@RequestMapping("/api/usersAnswers")
public class UsersAnswersController {
    private final UsersAnswersService usersAnswersService;
    private final UserService userService;
    private final QuestionService questionService;
    private final UsersTestsService usersTestsService;

    public UsersAnswersController(UsersAnswersService usersAnswersService, UserService userService, QuestionService questionService, UsersTestsService usersTestsService) {
        this.usersAnswersService = usersAnswersService;
        this.userService = userService;
        this.questionService = questionService;
        this.usersTestsService = usersTestsService;
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

    @DeleteMapping("/delete")
    public Integer deleteUsersAnswer(@RequestBody DeleteDTO deleteDTO) {
        System.out.println(deleteDTO);
        usersAnswersService.deleteById(deleteDTO.getId());
        return 1;

    }

}
