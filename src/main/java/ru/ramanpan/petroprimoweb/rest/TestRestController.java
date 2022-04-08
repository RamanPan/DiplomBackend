package ru.ramanpan.petroprimoweb.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ramanpan.petroprimoweb.DTO.CreateTestDTO;
import ru.ramanpan.petroprimoweb.DTO.TestDTO;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.model.enums.TestType;
import ru.ramanpan.petroprimoweb.service.TestService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/tests")
public class TestRestController {
    private final TestService testService;
    @Value("${upload.path.test}")
    private String uploadPath;
    public TestRestController(TestService testService) {
        this.testService = testService;
    }
    @PostMapping("/upload")
    public ResponseEntity.BodyBuilder uploadPicture(@RequestParam("file" )MultipartFile file) throws IOException {
        if(file!= null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String path = uploadPath + "/" + file.getOriginalFilename();
            System.out.println(path);
            file.transferTo(new File(path));
        }
        return ResponseEntity.ok();
    }

    @PostMapping("/create")
    public Long createTest(@RequestBody TestDTO testDTO) {
        Test test = new Test();
        test.setName(testDTO.getName());
        test.setAuthor(testDTO.getAuthor());
        test.setDescription(testDTO.getDescription());
        if(testDTO.getPicture().equals("")) test.setPicture("tests/plug.png");
        else test.setPicture("tests/"+ testDTO.getPicture());
        String test_role = testDTO.getTestType();
        if(test_role.equals("Стохастический")) test.setTestType(TestType.STOCHASTIC);
        else if(test_role.equals("Динамический")) test.setTestType(TestType.DYNAMIC);
        else test.setTestType(TestType.DETERMINISTIC);
        return testService.save(test);
    }

    @PostMapping("/delete")
    public ResponseEntity.BodyBuilder createTest(Long id) {
        testService.deleteById(id);
        return ResponseEntity.ok();
    }


}
