package ru.ramanpan.petroprimoweb.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ramanpan.petroprimoweb.DTO.*;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.enums.DifficultyQuestion;
import ru.ramanpan.petroprimoweb.model.enums.QuestionCategory;
import ru.ramanpan.petroprimoweb.model.enums.QuestionType;
import ru.ramanpan.petroprimoweb.service.QuestionService;
import ru.ramanpan.petroprimoweb.service.TestService;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final TestService testService;
    private final ModelMapper modelMapper;
    @Value("${upload.path.question}")
    private String uploadPath;

    private Set<AnswerDTO> getSetAnswerDTO(List<Answer> tests) {
        Set<AnswerDTO> answers = new HashSet<>();
        int i = 1; AnswerDTO answerDTO;
        for(Answer a : tests) {
            answerDTO = modelMapper.map(a,AnswerDTO.class);
            answerDTO.setNumber(i);
            answers.add(answerDTO);
            i++;}
        return answers;

    }

    public QuestionController(QuestionService questionService, TestService testService, ModelMapper modelMapper) {
        this.questionService = questionService;
        this.testService = testService;
        this.modelMapper = modelMapper;
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
    @PostMapping("/getAnswers")
    public Set<AnswerDTO> getAnswers(@RequestBody IdDTO id) {
        return getSetAnswerDTO(questionService.getAnswers(id.getId()));
    }


    @PostMapping("/create")
    public Long createQuestion(@RequestBody QuestionDTO questionDTO) {
        Question question = new Question();
        System.out.println(questionDTO);
        question.setStatement(questionDTO.getStatement());
        if(questionDTO.getPicture().equals(" ")) question.setPicture("plug.png");
        else question.setPicture(questionDTO.getPicture());
        question.setTest(testService.findById(questionDTO.getTestLong()));
        String questionType = questionDTO.getType();
        String questionDifficult = questionDTO.getDifficult();
        String questionCategory = questionDTO.getCategory();
        if(questionType.equals("Закрытый")) question.setType(QuestionType.CLOSED);
        else question.setType(QuestionType.OPEN);
        if(questionDifficult.equals("Легкая")) question.setDifficult(DifficultyQuestion.EASY);
        else if(questionDifficult.equals("Средняя")) question.setDifficult(DifficultyQuestion.MEDIUM);
        else question.setDifficult(DifficultyQuestion.HARD);
        if(questionCategory.equals("Политическая")) question.setCategory(QuestionCategory.POLITIC);
        else if(questionCategory.equals("Экономическая")) question.setCategory(QuestionCategory.ECONOMIC);
        else question.setCategory(QuestionCategory.CULTURE);
        return questionService.save(question);
    }
    @Transactional
    @DeleteMapping("/delete")
    public Integer deleteQuestion(@RequestBody DeleteDTO deleteDTO) {
        System.out.println(deleteDTO);
        questionService.deleteById(deleteDTO.getId());
        return 1;

    }
}
