package ru.ramanpan.petroprimoweb.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ramanpan.petroprimoweb.DTO.*;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.model.enums.TestType;
import ru.ramanpan.petroprimoweb.service.TestService;
import ru.ramanpan.petroprimoweb.service.UserService;

import javax.persistence.Id;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tests")
public class TestRestController {
    private final TestService testService;
    private final UserService userService;
    private List<Test> tests;
    private List<Question> actualTest;
    private final ModelMapper modelMapper;
    @Value("${upload.path.test}")
    private String uploadPath;

    private Set<TestCardDTO> getSetTestCardDTO(List<Test> tests) {
        Set<TestCardDTO> cards = new HashSet<>();
        for(Test t : tests) cards.add(modelMapper.map(t,TestCardDTO.class));
        return cards;

    }

    public TestRestController(TestService testService, UserService userService, ModelMapper modelMapper) {
        this.testService = testService;
        this.userService = userService;
        this.modelMapper = modelMapper;
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
        test.setMark(0.0);
        test.setNumberPasses(0);
        test.setNumberQuestions(0);
        if(testDTO.getPicture().equals("")) test.setPicture("plug.png");
        else test.setPicture(testDTO.getPicture());
        String test_role = testDTO.getTestType();
        if(test_role.equals("Стохастический")) test.setTestType(TestType.STOCHASTIC);
        else if(test_role.equals("Динамический")) test.setTestType(TestType.DYNAMIC);
        else test.setTestType(TestType.DETERMINISTIC);
        return testService.save(test).getId();
    }
    @GetMapping("/getTests")
    public Set<TestCardDTO> getTests() {
        return getSetTestCardDTO(testService.findAll());
    }

    @PostMapping("/getTest")
    public TestCardDTO getTest(@RequestBody UploadTestDTO id) {
        Test test = testService.findById(id.getId());
        actualTest = (List<Question>) test.getQuestions();
        Collections.shuffle(actualTest);
        return modelMapper.map(test,TestCardDTO.class);
    }
    @PostMapping("/getQuestion")
    public QuestDTO getQuestion(IdDTO idDTO) {
        Question q = actualTest.get((int) idDTO.getId());
        actualTest.remove(q);
        return modelMapper.map(q,QuestDTO.class);
    }


    @GetMapping("/getOldTests")
    public Set<TestCardDTO> getOldTests() {
        return getSetTestCardDTO(testService.findAll()).stream().sorted(Comparator.comparing(TestCardDTO::getCreated)).collect(Collectors.toCollection(LinkedHashSet::new));

    }
    @GetMapping("/getNewTests")
    public Set<TestCardDTO> getNewTests() {

        return getSetTestCardDTO(testService.findAll()).stream().sorted(Comparator.comparing(TestCardDTO::getCreated).reversed()).collect(Collectors.toCollection(LinkedHashSet::new));

    }
    @GetMapping("/getBestTests")
    public Set<TestCardDTO> getBestTests() {
        return getSetTestCardDTO(testService.findAll()).stream().sorted(Comparator.comparing(TestCardDTO::getMark).reversed()).collect(Collectors.toCollection(LinkedHashSet::new));
    }
    @PostMapping("/getFilterTests")
    public Set<TestCardDTO> getFilterTests(@RequestBody String filterType) {
        String type;
        filterType = filterType.substring(1,filterType.length()-1);
        switch (filterType) {
            case "Стохастический":
                type = TestType.STOCHASTIC.name();
                break;
            case "Динамический":
                type = TestType.DYNAMIC.name();
                break;
            case "Детерминированный":
                type = TestType.DETERMINISTIC.name();
                break;
            default:
                return getSetTestCardDTO(testService.findAll());
        }
        return getSetTestCardDTO(testService.findAll()).stream().filter(testCardDTO -> testCardDTO.getTestType().equals(type)).collect(Collectors.toCollection(LinkedHashSet::new));
    }
    @PostMapping("/getByAuthor")
    public Set<TestCardDTO> getTestsByAuthor(@RequestBody TestDTO testDTO) {
        return getSetTestCardDTO(testService.findByAuthor(testDTO.getAuthor()));
    }


    @PostMapping("/delete")
    public ResponseEntity.BodyBuilder deleteTest(@RequestBody DeleteDTO deleteDTO) {
        testService.deleteById(deleteDTO.getId());
        return ResponseEntity.ok();
    }
    @PostMapping("/setNumberQuestions")
    public Integer setNumberQuestions(@RequestBody UploadTestDTO testDTO) {
        Test test = testService.findById(testDTO.getId());
        test.setNumberQuestions(testDTO.getNumberQuestions());
        return testService.save(test).getNumberQuestions();
    }

    @PostMapping("/setNumberPasses")
    public Integer setNumberPasses(@RequestBody UploadTestDTO testDTO) {
        Test test = testService.findById(testDTO.getId());
        test.setNumberPasses(testDTO.getNumberPasses());
        return testService.save(test).getNumberPasses();
    }

}
