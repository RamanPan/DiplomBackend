package ru.ramanpan.petroprimoweb.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ramanpan.petroprimoweb.DTO.DeleteDTO;
import ru.ramanpan.petroprimoweb.DTO.ResultDTO;
import ru.ramanpan.petroprimoweb.model.Result;
import ru.ramanpan.petroprimoweb.service.ResultService;
import ru.ramanpan.petroprimoweb.service.TestService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/results")
public class ResultController {
    private final ResultService resultService;
    private final TestService testService;
    @Value("${upload.path.result}")
    private String uploadPath;

    public ResultController(ResultService resultService, TestService testService) {
        this.resultService = resultService;
        this.testService = testService;
    }

    @PostMapping("/upload")
    public Integer uploadPicture(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String path = uploadPath + "/" + file.getOriginalFilename();
            System.out.println(path);
            file.transferTo(new File(path));
        }
        return 1;
    }

    @PostMapping("/create")
    public Long createResult(@RequestBody ResultDTO resultDTO) {
        Result result = new Result();
        result.setDescription(resultDTO.getDescription());
        if (resultDTO.getPicture().equals(" ")) result.setPicture("plug.png");
        else result.setPicture(resultDTO.getPicture());
        result.setHeader(resultDTO.getHeader());
        result.setStartCondition(Double.valueOf(resultDTO.getStartCondition()));
        result.setEndCondition(Double.valueOf(resultDTO.getEndCondition()));
        result.setTest(testService.findById(resultDTO.getTestLong()));
        result.setCorrectness(resultDTO.getCorrectness());
        return resultService.save(result);
    }

    @PostMapping("/update")
    public Long updateResult(@RequestBody ResultDTO resultDTO) {
        Result result = resultService.findById(resultDTO.getId());
        result.setDescription(resultDTO.getDescription());
        result.setPicture(resultDTO.getPicture());
        result.setStartCondition(Double.valueOf(resultDTO.getStartCondition()));
        result.setEndCondition(Double.valueOf(resultDTO.getEndCondition()));
        result.setTest(testService.findById(resultDTO.getTestLong()));
        result.setCorrectness(resultDTO.getCorrectness());
        result.setHeader(resultDTO.getHeader());
        return resultService.save(result);
    }


    @DeleteMapping("/delete")
    public Integer deleteResult(@RequestBody DeleteDTO deleteDTO) {
        System.out.println(deleteDTO);
        resultService.deleteById(deleteDTO.getId());
        return 1;

    }
}
