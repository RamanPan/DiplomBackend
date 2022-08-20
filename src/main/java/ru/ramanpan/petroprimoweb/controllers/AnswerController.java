package ru.ramanpan.petroprimoweb.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ramanpan.petroprimoweb.DTO.AnswerDTO;
import ru.ramanpan.petroprimoweb.DTO.DeleteDTO;
import ru.ramanpan.petroprimoweb.service.AnswerService;

@RestController
@RequestMapping("/api/answers")
@AllArgsConstructor
public class AnswerController {
    private final AnswerService answerService;


    @DeleteMapping("/delete")
    public ResponseEntity<Integer> deleteAnswer(@RequestBody DeleteDTO deleteDTO) {
        answerService.deleteById(deleteDTO.getId());
        return ResponseEntity.ok(1);

    }

    @PostMapping("/create")
    public ResponseEntity<Long> createAnswer(@RequestBody AnswerDTO answerDTO) {
        return ResponseEntity.ok(answerService.save(answerDTO));

    }
}
