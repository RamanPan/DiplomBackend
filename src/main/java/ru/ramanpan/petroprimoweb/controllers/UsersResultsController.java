package ru.ramanpan.petroprimoweb.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ramanpan.petroprimoweb.dto.IdDTO;
import ru.ramanpan.petroprimoweb.dto.UserResultDTO;
import ru.ramanpan.petroprimoweb.dto.UsersResultsDTO;
import ru.ramanpan.petroprimoweb.model.UsersResults;
import ru.ramanpan.petroprimoweb.service.*;
import ru.ramanpan.petroprimoweb.util.Mapping;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/usersResults")
@AllArgsConstructor
public class UsersResultsController {
    private final UsersResultsService usersResultsService;
    private final UserService userService;
    private final UsersTestsService usersTestsService;


    @PostMapping("/getResults")
    public ResponseEntity<List<UserResultDTO>> getResults(@RequestBody IdDTO id) {
        List<UsersResults> usersResults = usersResultsService.findResultByUser(userService.findById(id.getId()));
        List<UserResultDTO> userResultDTOS = new ArrayList<>();

        for (UsersResults u : usersResults) {
            userResultDTOS.add(Mapping.toUserResultDTO(u));
        }

        return ResponseEntity.ok(userResultDTOS);
    }

    @PostMapping("/getResult")
    public ResponseEntity<UserResultDTO> getResult(@RequestBody UsersResultsDTO dto) {
        UsersResults userResult = usersResultsService.findResultByUserAndTest(userService.findById(dto.getUser()), usersTestsService.findById(dto.getUserTest()));
        return ResponseEntity.ok(Mapping.toUserResultDTO(userResult));
    }


}
