package ru.ramanpan.petroprimoweb.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ramanpan.petroprimoweb.DTO.DeleteDTO;
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
    public Integer uploadPicture(@RequestParam("file" ) MultipartFile file) throws IOException {
        if(file!= null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String path = uploadPath + "/" + file.getOriginalFilename();
            System.out.println(path);
            file.transferTo(new File(path));
        }
        return 1;
    }

    @PostMapping("/create")
    public Long createResult(@RequestBody ResultDTO resultDTO) {
        Result result = new Result();
        System.out.println(resultDTO);
        result.setDescription(resultDTO.getDescription());
        result.setPicture(resultDTO.getPicture());
        result.setHeader(resultDTO.getHeader());
        result.setStartCondition(Double.valueOf(resultDTO.getStartCondition()));
        result.setEndCondition(Double.valueOf(resultDTO.getEndCondition()));
        result.setTest(testService.findById(resultDTO.getTest()));
        result.setCorrectness(resultDTO.getCorrectness());
        return resultService.save(result);
    }
    @DeleteMapping("/delete")
    public Integer deleteResult(@RequestBody DeleteDTO deleteDTO) {
        System.out.println(deleteDTO);
        resultService.deleteById(deleteDTO.getId());
        return 1;

    }
}
