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
import ru.ramanpan.petroprimoweb.model.UsersAnswers;
import ru.ramanpan.petroprimoweb.model.enums.DeterministicOption;
import ru.ramanpan.petroprimoweb.model.enums.DifficultyQuestion;
import ru.ramanpan.petroprimoweb.model.enums.QuestionCategory;
import ru.ramanpan.petroprimoweb.model.enums.TestType;
import ru.ramanpan.petroprimoweb.service.TestService;
import ru.ramanpan.petroprimoweb.service.UserService;
import ru.ramanpan.petroprimoweb.service.UsersAnswersService;
import ru.ramanpan.petroprimoweb.util.Switches;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tests")
public class TestRestController {
    private final TestService testService;
    private final UserService userService;
    private final UsersAnswersService usersAnswersService;
    private List<Question> actualTest;
    private List<Question> removedQuestions;
    private Test test;
    private final ModelMapper modelMapper;
    private int countCorrectNonStop;
    private int countIncorrectNonStop;
    private int passedSuccessfully;
    private int passedUnsuccessful;
    @Value("${upload.path.test}")
    private String uploadPath;

    public TestRestController(TestService testService, UserService userService, UsersAnswersService usersAnswersService, ModelMapper modelMapper) {
        this.testService = testService;
        this.userService = userService;
        this.usersAnswersService = usersAnswersService;
        this.modelMapper = modelMapper;
    }

    private Set<TestCardDTO> getSetTestCardDTO(List<Test> tests) {
        Set<TestCardDTO> cards = new HashSet<>();
        for(Test t : tests) cards.add(modelMapper.map(t,TestCardDTO.class));
        return cards;

    }

    private void deterministicTest() {
        List<Question> hardQ = new ArrayList<>();
        List<Question> mediumQ = new ArrayList<>();
        List<Question> easyQ = new ArrayList<>();
        List<Question> politicQ = new ArrayList<>();
        List<Question> cultureQ = new ArrayList<>();
        List<Question> economicQ = new ArrayList<>();

        for (Question q : actualTest) {
            if(q.getDifficult().equals(DifficultyQuestion.EASY)) easyQ.add(q);
            else if(q.getDifficult().equals(DifficultyQuestion.MEDIUM)) mediumQ.add(q);
            else if(q.getDifficult().equals(DifficultyQuestion.HARD)) hardQ.add(q);
            if(q.getCategory().equals(QuestionCategory.CULTURE)) cultureQ.add(q);
            else if(q.getCategory().equals(QuestionCategory.POLITIC)) politicQ.add(q);
            else if(q.getCategory().equals(QuestionCategory.ECONOMIC)) economicQ.add(q);
        }
        actualTest = Switches.selectionOptionForDeterministic(test.getOptionForDeterministicType(),easyQ,mediumQ,hardQ,politicQ,cultureQ,economicQ);
    }

    private void actualTestWithRightDistribution() {
        List<Question> questionList = new ArrayList<>();
        int quantityCulture, quantityPolitic,quantityEconomic,quantityQuestions = test.getNumberQuestions();
        quantityCulture = (int)Math.ceil((double) test.getPercentCulture() * quantityQuestions / 100);
        quantityPolitic = (int)Math.ceil((double) test.getPercentPolitic() * quantityQuestions / 100);
        quantityEconomic = (int)Math.ceil((double) test.getPercentEconomic() * quantityQuestions / 100);
        int i = 0;
        for (Question q : actualTest) {
            if(i == quantityQuestions) break;
            if(q.getCategory().equals(QuestionCategory.POLITIC) && quantityPolitic != 0) {
                questionList.add(q);
                quantityPolitic--;
                i++;
            }
            else if(q.getCategory().equals(QuestionCategory.CULTURE) && quantityCulture != 0) {
                questionList.add(q);
                quantityCulture--;
                i++;
            }
            else if(q.getCategory().equals(QuestionCategory.ECONOMIC) && quantityEconomic != 0) {
                questionList.add(q);
                quantityEconomic--;
                i++;
            }
        }
        actualTest = questionList;
        System.out.println(actualTest.size());
    }

    private Question selectionQuestionForDynamicTest(int index, long idUserAnswer) {
        Question q = null,lastQuestion,qE = null, qM = null, qH = null; String difficult = "";
        if(removedQuestions.isEmpty()) {
            if(passedSuccessfully > passedUnsuccessful) difficult = "HARD";
            else if(passedSuccessfully < passedUnsuccessful) difficult = "EASY";
            else difficult = "MEDIUM";
        }
        else {
            UsersAnswers usersAnswers = usersAnswersService.findById(idUserAnswer);
            lastQuestion = removedQuestions.get(index-1);
            if(usersAnswers.getCorrect()) {
                countCorrectNonStop++;
                countIncorrectNonStop = 0;
            }
            else {
                countIncorrectNonStop++;
                countCorrectNonStop = 0;
            }
            if(lastQuestion.getDifficult().equals(DifficultyQuestion.HARD)) {
                if(countIncorrectNonStop > 1) {difficult = "MEDIUM";countIncorrectNonStop = 0;}
                else difficult = "HARD";
            }
            else if(lastQuestion.getDifficult().equals(DifficultyQuestion.MEDIUM)) {
                    if(countCorrectNonStop > 1) difficult = "HARD";
                    else if(countIncorrectNonStop > 1) difficult = "EASY";
                    else difficult = "MEDIUM";
            }
            else if(lastQuestion.getDifficult().equals(DifficultyQuestion.EASY)) {
                if(countCorrectNonStop > 1) {difficult = "MEDIUM";countCorrectNonStop = 0;}
                else difficult = "EASY";
            }

        }
        for(Question quest : actualTest) {
            if(quest.getDifficult().toString().equals(difficult)) {
                q = quest;
                break;
            }
            if(quest.getDifficult().equals(DifficultyQuestion.EASY)) qE = quest;
            else if(quest.getDifficult().equals(DifficultyQuestion.MEDIUM)) qM = quest;
            else qH = quest;
        }
        if(q == null) {
        switch (difficult) {
            case "HARD" :
                if(qH != null) q = qH;
                else if(qM != null) q = qM;
                else q = qE;
                break;
            case "MEDIUM" :
                if(qM != null) q = qM;
                else if(qH != null) q = qH;
                else q = qE;
                break;
            case "EASY" :
                if(qE != null) q = qE;
                else if(qM != null) q = qM;
                else q = qH;
                break;
            default:
                q = actualTest.get(index);
        }
        }

        removedQuestions.add(q);
        actualTest.remove(q);
        return q;
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
        User u = userService.findByNickname(testDTO.getAuthor());
        u.setCountCreated(u.getCountCreated()+1);
        userService.update(u);
        if(testDTO.getPicture().equals("")) test.setPicture("plug.png");
        else test.setPicture(testDTO.getPicture());
        String testType = testDTO.getTestType();
        test.setTestType(Switches.selectionTestType(testType));
        if(test.getTestType().equals(TestType.DETERMINISTIC)) {
            String option = testDTO.getOptionForDeterministicType();
            test.setOptionForDeterministicType(Switches.selectionOption(option));}
        return testService.save(test).getId();
    }
    @GetMapping("/getTests")
    public Set<TestCardDTO> getTests() {
        return getSetTestCardDTO(testService.findAll());
    }

    @PostMapping("/getTest")
    public TestCardDTO getTest(@RequestBody TestDTO id) {
        User user = userService.findById(id.getUserId());
        test = testService.findById(id.getId());
        actualTest = test.getQuestions();
        removedQuestions = new ArrayList<>();
        countCorrectNonStop = 0; countIncorrectNonStop = 0;
        passedSuccessfully = user.getCountPassedCorrect();
        passedUnsuccessful = user.getCountPassedIncorrect();
        if(test.getPercentCulture() > -1) {actualTestWithRightDistribution();
            System.out.println(actualTest);}
        if(!test.getTestType().equals(TestType.DETERMINISTIC)) Collections.shuffle(actualTest);
        else deterministicTest();
        return modelMapper.map(test,TestCardDTO.class);
    }
    @PostMapping("/getQuestion")
    public QuestionDTO getQuestion(@RequestBody IdDTO idDTO) {
        Question q;
        if(!test.getTestType().equals(TestType.DYNAMIC)) {
            q = actualTest.get(0);
            removedQuestions.add(q);
            actualTest.remove(q);}
        else q = selectionQuestionForDynamicTest((int) idDTO.getId(), idDTO.getIdUserAnswer());
        System.out.println(q);
        return modelMapper.map(q,QuestionDTO.class);
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
    @PostMapping("/setNQ")
    public Long setNQ(@RequestBody IdDTO dto) {
        Test test = testService.findById(dto.getId());
        test.setNumberQuestions(test.getQuestions().size());
        return  testService.update(test).getId();
    }

    @PostMapping("/setPercents")
    public Long setPercentsQuestions(@RequestBody TestDTO dto) {
        Test test = testService.findById(dto.getId());
        test.setPercentCulture(dto.getPercentCulture());
        test.setPercentEconomic(dto.getPercentEconomic());
        test.setPercentPolitic(dto.getPercentPolitic());
        test.setNumberQuestions(Integer.valueOf(dto.getNumberQuestions()));
        return testService.update(test).getId();
    }

    @PostMapping("/setNumberPasses")
    public Integer setNumberPasses(@RequestBody TestDTO testDTO) {
        Test test = testService.findById(testDTO.getId());
        test.setNumberPasses(testDTO.getNumberPasses());
        return testService.save(test).getNumberPasses();
    }

}
