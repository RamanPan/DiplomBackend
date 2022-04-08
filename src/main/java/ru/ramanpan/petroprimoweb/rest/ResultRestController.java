package ru.ramanpan.petroprimoweb.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ramanpan.petroprimoweb.DTO.QuestionDTO;
import ru.ramanpan.petroprimoweb.DTO.ResultDTO;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.Result;
import ru.ramanpan.petroprimoweb.model.enums.DifficultyQuestion;
import ru.ramanpan.petroprimoweb.model.enums.QuestionCategory;
import ru.ramanpan.petroprimoweb.model.enums.QuestionType;
import ru.ramanpan.petroprimoweb.service.ResultService;
import ru.ramanpan.petroprimoweb.service.TestService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/results")
public class ResultRestController {
    private final ResultService resultService;
    private final TestService testService;
    @Value("${upload.path.result}")
    private String uploadPath;

    public ResultRestController(ResultService resultService, TestService testService) {
        this.resultService = resultService;
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
    public Long createQuestion(@RequestBody ResultDTO resultDTO) {
        Result result = new Result();
        result.setDescription(resultDTO.getDescription());
        result.setPicture("results/"+resultDTO.getPicture());
        result.setTest(testService.findById(resultDTO.getTest()));
        return resultService.save(result);
    }
}
