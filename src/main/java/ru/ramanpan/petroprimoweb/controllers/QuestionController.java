package ru.ramanpan.petroprimoweb.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ramanpan.petroprimoweb.DTO.AnswerDTO;
import ru.ramanpan.petroprimoweb.DTO.DeleteDTO;
import ru.ramanpan.petroprimoweb.DTO.IdDTO;
import ru.ramanpan.petroprimoweb.DTO.QuestionDTO;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.service.QuestionService;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
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
            String path = String.format("%s/%s", uploadPath, file.getOriginalFilename());
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
