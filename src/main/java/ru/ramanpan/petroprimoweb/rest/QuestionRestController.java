package ru.ramanpan.petroprimoweb.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ramanpan.petroprimoweb.DTO.CreateTestDTO;
import ru.ramanpan.petroprimoweb.DTO.QuestionDTO;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.model.enums.DifficultyQuestion;
import ru.ramanpan.petroprimoweb.model.enums.QuestionCategory;
import ru.ramanpan.petroprimoweb.model.enums.QuestionType;
import ru.ramanpan.petroprimoweb.model.enums.TestType;
import ru.ramanpan.petroprimoweb.service.QuestionService;
import ru.ramanpan.petroprimoweb.service.TestService;
import ru.ramanpan.petroprimoweb.service.impl.QuestionServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/questions")
public class QuestionRestController {
    private final QuestionService questionService;
    private final TestService testService;
    @Value("${upload.path.question}")
    private String uploadPath;
    public QuestionRestController(QuestionService questionService, TestService testService) {
        this.questionService = questionService;
        this.testService = testService;
    }

    @PostMapping("/upload")
    public ResponseEntity.BodyBuilder uploadPicture(@RequestParam("file" ) MultipartFile file) throws IOException {
        if(file!= null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String path = uploadPath + "/" + file.getOriginalFilename();
            System.out.println(path);
            file.transferTo(new File(path));
        }
        return ResponseEntity.ok();
    }

    @PostMapping("/create")
    public Long createQuestion(@RequestBody QuestionDTO questionDTO) {
        Question question = new Question();
        question.setStatement(questionDTO.getStatement());
        if(questionDTO.getPicture().equals(" ")) question.setPicture("questions/plug.png");
        else question.setPicture("questions/"+questionDTO.getPicture());
        question.setTest(testService.findById(questionDTO.getTest()));
        String questionType = questionDTO.getType();
        String questionDifficult = questionDTO.getDifficult();
        String questionCategory = questionDTO.getCategory();
        if(questionType.equals("Закрытый")) question.setType(QuestionType.CLOSED);
        else question.setType(QuestionType.OPEN);
        if(questionDifficult.equals("Легкая")) question.setDifficult(DifficultyQuestion.EASY);
        else if(questionDifficult.equals("Средняя")) question.setDifficult(DifficultyQuestion.MEDIUM);
        else question.setDifficult(DifficultyQuestion.HARD);
        if(questionCategory.equals("Политическая")) question.setCategory(QuestionCategory.POLITIC);
        else if(questionDifficult.equals("Культурная")) question.setCategory(QuestionCategory.CULTURE);
        else question.setCategory(QuestionCategory.ECONOMIC);
        return questionService.save(question);
    }
}
