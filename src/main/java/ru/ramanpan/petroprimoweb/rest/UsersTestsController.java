package ru.ramanpan.petroprimoweb.rest;


import org.springframework.web.bind.annotation.*;
import ru.ramanpan.petroprimoweb.DTO.IdDTO;
import ru.ramanpan.petroprimoweb.DTO.UsersAnswersDTO;
import ru.ramanpan.petroprimoweb.DTO.UsersResultsDTO;
import ru.ramanpan.petroprimoweb.DTO.UsersTestsDTO;
import ru.ramanpan.petroprimoweb.model.UsersAnswers;
import ru.ramanpan.petroprimoweb.model.UsersTests;
import ru.ramanpan.petroprimoweb.model.enums.Correctness;
import ru.ramanpan.petroprimoweb.service.*;

@RestController
@RequestMapping("/api/usersTests")
public class UsersTestsController {
    private final UsersTestsService usersTestsService;
    private final UserService userService;
    private final TestService testService;

    public UsersTestsController(UsersTestsService usersTestsService, UserService userService, TestService testService) {
        this.usersTestsService = usersTestsService;
        this.userService = userService;
        this.testService = testService;
    }

    @PostMapping("/create")
    public Long createUsersTest(@RequestBody UsersTestsDTO usersTestsDTO) {
        UsersTests usersTests = new UsersTests();
        usersTests.setMark(usersTestsDTO.getMark());
        usersTests.setTest(testService.findById(usersTestsDTO.getTest()));
        usersTests.setUser(userService.findById(usersTestsDTO.getUser()));
        return usersTestsService.save(usersTests).getId();

    }
    @PostMapping("/setPassed")
    public Long setPassed(@RequestBody UsersResultsDTO usersResultsDTO) {
        System.out.println(usersResultsDTO);
        UsersTests usersTests = usersTestsService.findById(usersResultsDTO.getUserTest());
        return usersTestsService.update(usersTests,usersResultsDTO);

    }

}
