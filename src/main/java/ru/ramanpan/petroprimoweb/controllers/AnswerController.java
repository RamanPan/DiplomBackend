package ru.ramanpan.petroprimoweb.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ramanpan.petroprimoweb.dto.AnswerDTO;
import ru.ramanpan.petroprimoweb.service.AnswerService;

@RestController
@RequestMapping("/api/answers")
@AllArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Integer> deleteAnswer(@PathVariable("id") Long id) {
        answerService.deleteById(id);
        return ResponseEntity.ok(1);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createAnswer(@RequestBody AnswerDTO answerDTO) {
        return ResponseEntity.ok(answerService.save(answerDTO).getId());
    }
}
