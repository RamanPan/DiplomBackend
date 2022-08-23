package ru.ramanpan.petroprimoweb.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ramanpan.petroprimoweb.dto.*;
import ru.ramanpan.petroprimoweb.model.*;
import ru.ramanpan.petroprimoweb.model.enums.DifficultyQuestion;
import ru.ramanpan.petroprimoweb.model.enums.QuestionCategory;
import ru.ramanpan.petroprimoweb.model.enums.Status;
import ru.ramanpan.petroprimoweb.model.enums.TestType;
import ru.ramanpan.petroprimoweb.service.TestService;
import ru.ramanpan.petroprimoweb.service.UserService;
import ru.ramanpan.petroprimoweb.service.UsersAnswersService;
import ru.ramanpan.petroprimoweb.util.Constants;
import ru.ramanpan.petroprimoweb.util.Switches;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tests")
public class TestController {
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

    public TestController(TestService testService, UserService userService, UsersAnswersService usersAnswersService, ModelMapper modelMapper) {
        this.testService = testService;
        this.userService = userService;
        this.usersAnswersService = usersAnswersService;
        this.modelMapper = modelMapper;
    }

    private Set<TestCardDTO> getSetTestCardDTO(List<Test> tests) {
        Set<TestCardDTO> cards = new HashSet<>();
        for (Test t : tests) cards.add(modelMapper.map(t, TestCardDTO.class));
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
            if (q.getDifficult().equals(DifficultyQuestion.EASY)) easyQ.add(q);
            else if (q.getDifficult().equals(DifficultyQuestion.MEDIUM)) mediumQ.add(q);
            else if (q.getDifficult().equals(DifficultyQuestion.HARD)) hardQ.add(q);
            if (q.getCategory().equals(QuestionCategory.CULTURE)) cultureQ.add(q);
            else if (q.getCategory().equals(QuestionCategory.POLITIC)) politicQ.add(q);
            else if (q.getCategory().equals(QuestionCategory.ECONOMIC)) economicQ.add(q);
        }
        actualTest = Switches.selectionOptionForDeterministic(test.getOptionForDeterministicType(), easyQ, mediumQ, hardQ, politicQ, cultureQ, economicQ);
    }

    private void actualTestWithRightDistribution() {
        List<Question> questionList = new ArrayList<>();
        int quantityCulture, quantityPolitic, quantityEconomic, quantityQuestions = test.getNumberQuestions();
        quantityCulture = (int) Math.ceil((double) test.getPercentCulture() * quantityQuestions / 100);
        quantityPolitic = (int) Math.ceil((double) test.getPercentPolitic() * quantityQuestions / 100);
        quantityEconomic = (int) Math.ceil((double) test.getPercentEconomic() * quantityQuestions / 100);
        int i = 0;
        for (Question q : actualTest) {
            if (i == quantityQuestions) break;
            if (q.getCategory().equals(QuestionCategory.POLITIC) && quantityPolitic != 0) {
                questionList.add(q);
                quantityPolitic--;
                i++;
            } else if (q.getCategory().equals(QuestionCategory.CULTURE) && quantityCulture != 0) {
                questionList.add(q);
                quantityCulture--;
                i++;
            } else if (q.getCategory().equals(QuestionCategory.ECONOMIC) && quantityEconomic != 0) {
                questionList.add(q);
                quantityEconomic--;
                i++;
            }
        }
        actualTest = questionList;
    }

    private Question selectionQuestionForDynamicTest(int index, long idUserAnswer) {
        Question q = null, lastQuestion, qE = null, qM = null, qH = null;
        String difficult = "";
        if (removedQuestions.isEmpty()) {
            if (passedSuccessfully > passedUnsuccessful) difficult = Constants.HARD;
            else if (passedSuccessfully < passedUnsuccessful) difficult = Constants.EASY;
            else difficult = Constants.MEDIUM;
        } else {
            UsersAnswers usersAnswers = usersAnswersService.findById(idUserAnswer);
            lastQuestion = removedQuestions.get(index - 1);
            if (Boolean.TRUE.equals(usersAnswers.getCorrect())) {
                countCorrectNonStop++;
                countIncorrectNonStop = 0;
            } else {
                countIncorrectNonStop++;
                countCorrectNonStop = 0;
            }
            if (lastQuestion.getDifficult().equals(DifficultyQuestion.HARD)) {
                if (countIncorrectNonStop > 1) {
                    difficult = Constants.MEDIUM;
                    countIncorrectNonStop = 0;
                } else difficult = Constants.HARD;
            } else if (lastQuestion.getDifficult().equals(DifficultyQuestion.MEDIUM)) {
                if (countCorrectNonStop > 1) difficult = Constants.HARD;
                else if (countIncorrectNonStop > 1) difficult = Constants.EASY;
                else difficult = Constants.MEDIUM;
            } else if (lastQuestion.getDifficult().equals(DifficultyQuestion.EASY)) {
                if (countCorrectNonStop > 1) {
                    difficult = Constants.MEDIUM;
                    countCorrectNonStop = 0;
                } else difficult = Constants.EASY;
            }

        }
        for (Question quest : actualTest) {
            if (quest.getDifficult().toString().equals(difficult)) {
                q = quest;
                break;
            }
            if (quest.getDifficult().equals(DifficultyQuestion.EASY)) qE = quest;
            else if (quest.getDifficult().equals(DifficultyQuestion.MEDIUM)) qM = quest;
            else qH = quest;
        }
        if (q == null) {
            switch (difficult) {
                case Constants.HARD:
                    if (qH != null) q = qH;
                    else if (qM != null) q = qM;
                    else q = qE;
                    break;
                case Constants.MEDIUM:
                    if (qM != null) q = qM;
                    else if (qH != null) q = qH;
                    else q = qE;
                    break;
                case Constants.EASY:
                    if (qE != null) q = qE;
                    else if (qM != null) q = qM;
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
    public ResponseEntity.BodyBuilder uploadPicture(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String path = String.format("%s/%s", uploadPath, file.getOriginalFilename());
            file.transferTo(new File(path));
        }
        return ResponseEntity.ok();
    }

    @PostMapping("/create")
    public Long createTest(@RequestBody TestDTO testDTO) {
        return testService.save(testDTO).getId();
    }

    @PostMapping("/update")
    public Long updateTest(@RequestBody TestDTO testDTO) {
        return testService.update(testDTO).getId();
    }

    @GetMapping("/getTests")
    public Set<TestCardDTO> getTests() {
        return getSetTestCardDTO(testService.findAll()).stream().filter(testCardDTO -> !Constants.DELETED.equals(testCardDTO.getStatus())).collect(Collectors.toCollection(HashSet::new));
    }

    @PostMapping("/getTest")
    public TestCardDTO getTest(@RequestBody TestDTO id) {
        User user = userService.findById(id.getUserId());
        test = testService.findById(id.getId());
        actualTest = test.getQuestions();
        removedQuestions = new ArrayList<>();
        countCorrectNonStop = 0;
        countIncorrectNonStop = 0;
        passedSuccessfully = user.getCountPassedCorrect();
        passedUnsuccessful = user.getCountPassedIncorrect();
        if (test.getPercentCulture() > -1) actualTestWithRightDistribution();
        if (!test.getTestType().equals(TestType.DETERMINISTIC)) Collections.shuffle(actualTest);
        else deterministicTest();
        TestCardDTO testCardDTO = modelMapper.map(test, TestCardDTO.class);
        testCardDTO.setIsDeterministic(false);
        return testCardDTO;
    }

    @GetMapping("/getTestForUpdate/{id}")
    public TestCardDTO getTestForUpdate(@PathVariable("id") Long id) {
        Test t = testService.findById(id);
        return Switches.testCardForUpdate(modelMapper.map(t, TestCardDTO.class), t);
    }


    @PostMapping("/getQuestion")
    public QuestionDTO getQuestion(@RequestBody IdDTO idDTO) {
        Question q;
        if (!test.getTestType().equals(TestType.DYNAMIC)) {
            q = actualTest.get(0);
            removedQuestions.add(q);
            actualTest.remove(q);
        } else q = selectionQuestionForDynamicTest((int) idDTO.getId(), idDTO.getIdUserAnswer());
        return modelMapper.map(q, QuestionDTO.class);
    }

    @GetMapping("/getQuestions/{id}")
    public List<QuestionDTO> getQuestions(@PathVariable("id") Long id) {
        List<QuestionDTO> questions = new ArrayList<>();
        int i = 1;
        for (Question q : testService.findById(id).getQuestions()) {
            questions.add(Switches.questionForUpdate(modelMapper.map(q, QuestionDTO.class), q, i));
            i++;
        }
        return questions;
    }

    @GetMapping("/getResults/{id}")
    public List<ResultDTO> getResults(@PathVariable("id") Long id) {
        List<ResultDTO> results = new ArrayList<>();
        int i = 1;
        for (Result r : testService.findById(id).getResults()) {
            results.add(Switches.resultForUpdate(modelMapper.map(r, ResultDTO.class), i));
            i++;
        }
        return results;
    }

    @GetMapping("/getPercents/{id}")
    public List<Integer> getPercents(@PathVariable("id")Long id) {
        List<Integer> percents = new ArrayList<>();
        Test testFind = testService.findById(id);
        percents.add(testFind.getNumberQuestions());
        if (testFind.getPercentPolitic() > -1) {
            percents.add(testFind.getPercentCulture());
            percents.add(testFind.getPercentPolitic());
            percents.add(testFind.getPercentEconomic());
        } else {
            percents.add(0);
            percents.add(0);
            percents.add(0);
        }
        return percents;
    }


    @GetMapping("/getOldTests")
    public Set<TestCardDTO> getOldTests() {
        return getSetTestCardDTO(testService.findAll()).stream().filter(testCardDTO -> !testCardDTO.getStatus().equals(Constants.DELETED)).sorted(Comparator.comparing(TestCardDTO::getCreated)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @GetMapping("/getNewTests")
    public Set<TestCardDTO> getNewTests() {
        return getSetTestCardDTO(testService.findAll()).stream().filter(testCardDTO -> !testCardDTO.getStatus().equals(Constants.DELETED)).sorted(Comparator.comparing(TestCardDTO::getCreated).reversed()).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @GetMapping("/getBestTests")
    public Set<TestCardDTO> getBestTests() {
        return getSetTestCardDTO(testService.findAll()).stream().filter(testCardDTO -> !testCardDTO.getStatus().equals(Constants.DELETED)).sorted(Comparator.comparing(TestCardDTO::getMark).reversed()).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @PostMapping("/getFilterTests")
    public Set<TestCardDTO> getFilterTests(@RequestBody String filterType) {
        String type;
        filterType = filterType.substring(1, filterType.length() - 1);
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
                return getSetTestCardDTO(testService.findAll()).stream().filter(testCardDTO -> !testCardDTO.getStatus().equals(Constants.DELETED)).collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return getSetTestCardDTO(testService.findAll()).stream().filter(testCardDTO -> testCardDTO.getTestType().equals(type)).filter(testCardDTO -> !testCardDTO.getStatus().equals(Constants.DELETED)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @PostMapping("/getByAuthor")
    public Set<TestCardDTO> getTestsByAuthor(@RequestBody TestDTO testDTO) {
        return getSetTestCardDTO(testService.findByAuthor(testDTO.getAuthor())).stream().filter(test1 -> !test1.getStatus().equals(Status.DELETED.name())).collect(Collectors.toCollection(LinkedHashSet::new));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity.BodyBuilder deleteTest(@PathVariable("id") Long id) {
        testService.deleteById(id);
        return ResponseEntity.ok();
    }

    @PostMapping("/setNQ/{id}")
    public ResponseEntity<Long> setNQ(@PathVariable("id") Long id) {
        Test test = testService.findById(id);
        test.setNumberQuestions(test.getQuestions().size());
        return ResponseEntity.ok(testService.specificUpdate(test).getId());
    }

    @PostMapping("/setPercents")
    public ResponseEntity<Long> setPercentsQuestions(@RequestBody TestDTO dto) {
        Test test = testService.findById(dto.getId());
        test.setPercentCulture(dto.getPercentCulture());
        test.setPercentEconomic(dto.getPercentEconomic());
        test.setPercentPolitic(dto.getPercentPolitic());
        test.setNumberQuestions(Integer.valueOf(dto.getNumberQuestions()));
        return ResponseEntity.ok(testService.specificUpdate(test).getId());
    }

    @PostMapping("/setNumberPasses")
    public ResponseEntity<Integer> setNumberPasses(@RequestBody TestDTO testDTO) {
        Test test = testService.findById(testDTO.getId());
        test.setNumberPasses(testDTO.getNumberPasses());
        return ResponseEntity.ok(testService.specificUpdate(test).getNumberPasses());
    }

}
