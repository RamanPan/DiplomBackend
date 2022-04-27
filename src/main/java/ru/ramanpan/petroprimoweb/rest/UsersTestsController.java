package ru.ramanpan.petroprimoweb.rest;


import org.springframework.web.bind.annotation.*;
import ru.ramanpan.petroprimoweb.DTO.*;
import ru.ramanpan.petroprimoweb.model.UsersAnswers;
import ru.ramanpan.petroprimoweb.model.UsersResults;
import ru.ramanpan.petroprimoweb.model.UsersTests;
import ru.ramanpan.petroprimoweb.model.enums.Correctness;
import ru.ramanpan.petroprimoweb.service.*;
import ru.ramanpan.petroprimoweb.util.Mapping;

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
    public UserResultDTO setPassed(@RequestBody UsersResultsDTO usersResultsDTO) {
        System.out.println(usersResultsDTO);
        UsersTests usersTests = usersTestsService.findById(usersResultsDTO.getUserTest());
        return Mapping.toUserResultDTO(usersTestsService.setResultToTest(usersTests,usersResultsDTO));

    }
    @PostMapping("/setMark")
    public Long setMark(@RequestBody UsersTestsDTO dto) {
        UsersTests u = usersTestsService.findById(dto.getId());
        System.out.println(dto);
        u.setMark(dto.getMark());
        return usersTestsService.setMark(u).getId();
    }


}
