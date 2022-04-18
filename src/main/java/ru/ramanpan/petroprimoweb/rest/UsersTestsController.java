package ru.ramanpan.petroprimoweb.rest;


import org.springframework.web.bind.annotation.*;
import ru.ramanpan.petroprimoweb.DTO.*;
import ru.ramanpan.petroprimoweb.model.UsersAnswers;
import ru.ramanpan.petroprimoweb.model.UsersResults;
import ru.ramanpan.petroprimoweb.model.UsersTests;
import ru.ramanpan.petroprimoweb.model.enums.Correctness;
import ru.ramanpan.petroprimoweb.service.*;

@RestController
@RequestMapping("/api/usersTests")
public class UsersTestsController {
    private final UsersTestsService usersTestsService;
    private final UserService userService;
    private final TestService testService;
    private UserResultDTO toUserResultDTO(UsersResults usersResults) {
        UserResultDTO u = new UserResultDTO();
        u.setId(usersResults.getId());
        u.setResult(usersResults.getResultNum());
        u.setCorrectness(usersResults.getResult().getCorrectness());
        u.setHeader(usersResults.getResult().getHeader());
        u.setDescription(usersResults.getResult().getDescription());
        u.setPicture(usersResults.getResult().getPicture());
        u.setName(usersResults.getTest().getTest().getName());
        u.setCreated(usersResults.getCreated());
        return u;
    }

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
    public UserResultDTO setPassed(@RequestBody UsersResultsDTO usersResultsDTO) {
        System.out.println(usersResultsDTO);
        UsersTests usersTests = usersTestsService.findById(usersResultsDTO.getUserTest());
        return toUserResultDTO(usersTestsService.setResultToTest(usersTests,usersResultsDTO));

    }
    @PostMapping("/setMark")
    public Long setMark(@RequestBody UsersTestsDTO dto) {
        UsersTests u = usersTestsService.findById(dto.getId());
        u.setMark(dto.getMark());
        return usersTestsService.setMark(u).getId();
    }


}
