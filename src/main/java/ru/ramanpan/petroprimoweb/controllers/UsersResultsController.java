package ru.ramanpan.petroprimoweb.controllers;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ramanpan.petroprimoweb.DTO.IdDTO;
import ru.ramanpan.petroprimoweb.DTO.UserResultDTO;
import ru.ramanpan.petroprimoweb.DTO.UsersResultsDTO;
import ru.ramanpan.petroprimoweb.model.UsersResults;
import ru.ramanpan.petroprimoweb.service.*;
import ru.ramanpan.petroprimoweb.util.Mapping;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/usersResults")
public class UsersResultsController {
    private final UsersResultsService usersResultsService;
    private final UserService userService;
    private final ResultService resultService;
    private final UsersTestsService usersTestsService;




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
            userResultDTOS.add(Mapping.toUserResultDTO(u));
        }

        return userResultDTOS;
    }
    @PostMapping("/getResult")
    public UserResultDTO getResult(@RequestBody UsersResultsDTO dto) {
        UsersResults userResult = usersResultsService.findResultByUserAndTest(userService.findById(dto.getUser()),usersTestsService.findById(dto.getUserTest()));
        return Mapping.toUserResultDTO(userResult);
    }


}
