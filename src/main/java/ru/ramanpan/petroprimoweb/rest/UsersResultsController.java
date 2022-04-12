package ru.ramanpan.petroprimoweb.rest;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ramanpan.petroprimoweb.DTO.IdDTO;
import ru.ramanpan.petroprimoweb.DTO.UserResultDTO;
import ru.ramanpan.petroprimoweb.DTO.UsersResultsDTO;
import ru.ramanpan.petroprimoweb.DTO.UsersTestsDTO;
import ru.ramanpan.petroprimoweb.model.UsersResults;
import ru.ramanpan.petroprimoweb.model.UsersTests;
import ru.ramanpan.petroprimoweb.service.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/usersResults")
public class UsersResultsController {
    private final UsersResultsService usersResultsService;
    private final UserService userService;
    private final ResultService resultService;
    private final UsersTestsService usersTestsService;

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



    public UsersResultsController(UsersResultsService usersResultsService, UserService userService, ResultService resultService, UsersTestsService usersTestsService) {
        this.usersResultsService = usersResultsService;
        this.userService = userService;
        this.resultService = resultService;
        this.usersTestsService = usersTestsService;
    }

    @PostMapping("/getResults")
    public List<UserResultDTO> getResults(@RequestBody IdDTO id) {
        List<UsersResults> usersResults = usersResultsService.findResultByUser(userService.findById(id.getId()));
        List<UserResultDTO> userResultDTOS = new ArrayList<>();

        for(UsersResults u : usersResults) {
            userResultDTOS.add(toUserResultDTO(u));
        }

        return userResultDTOS;
    }

}
