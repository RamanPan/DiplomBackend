package ru.ramanpan.petroprimoweb.controllers;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final ModelMapper modelMapper;
    @Value("${upload.path.question}")
    private String uploadPath;

    private Set<AnswerDTO> getSetAnswerDTO(List<Answer> tests) {
        Set<AnswerDTO> answers = new HashSet<>();
        int i = 1;
        AnswerDTO answerDTO;
        for (Answer a : tests) {
            answerDTO = modelMapper.map(a, AnswerDTO.class);
            answerDTO.setNumber(i);
            answers.add(answerDTO);
            i++;
        }
        return answers;

    }

    @PostMapping("/upload")
    public ResponseEntity.BodyBuilder uploadPicture(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String path = uploadPath + "/" + file.getOriginalFilename();
            System.out.println(path);
            file.transferTo(new File(path));
        }
        return ResponseEntity.ok();
    }

    @PostMapping("/getAnswers")
    public ResponseEntity<Set<AnswerDTO>> getAnswers(@RequestBody IdDTO id) {
        return ResponseEntity.ok(getSetAnswerDTO(questionService.getAnswers(id.getId())));
    }


    @PostMapping("/create")
    public ResponseEntity<Long> createQuestion(@RequestBody QuestionDTO questionDTO) {
        return ResponseEntity.ok(questionService.save(questionDTO));
    }

    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity.BodyBuilder deleteQuestion(@RequestBody DeleteDTO deleteDTO) {
        questionService.deleteById(deleteDTO.getId());
        return ResponseEntity.ok();

    }
}
