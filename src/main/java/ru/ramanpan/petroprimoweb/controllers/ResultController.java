package ru.ramanpan.petroprimoweb.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ramanpan.petroprimoweb.DTO.DeleteDTO;
import ru.ramanpan.petroprimoweb.DTO.ResultDTO;
import ru.ramanpan.petroprimoweb.model.Result;
import ru.ramanpan.petroprimoweb.service.ResultService;
import ru.ramanpan.petroprimoweb.service.TestService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/api/results")
public class ResultController {
    private final ResultService resultService;
    @Value("${upload.path.result}")
    private String uploadPath;

    @PostMapping("/upload")
    public ResponseEntity.BodyBuilder uploadPicture(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String path = uploadPath + "/" + file.getOriginalFilename();
            System.out.println(path);
            file.transferTo(new File(path));
        }
        return ResponseEntity.ok();
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createResult(@RequestBody ResultDTO resultDTO) {
        return ResponseEntity.ok(resultService.save(resultDTO));
    }

    @PostMapping("/update")
    public ResponseEntity<Long> updateResult(@RequestBody ResultDTO resultDTO) {
        return ResponseEntity.ok(resultService.update(resultDTO));
    }


    @DeleteMapping("/delete")
    public ResponseEntity.BodyBuilder deleteResult(@RequestBody DeleteDTO deleteDTO) {
        System.out.println(deleteDTO);
        resultService.deleteById(deleteDTO.getId());
        return ResponseEntity.ok();

    }
}
