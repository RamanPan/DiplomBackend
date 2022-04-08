package ru.ramanpan.petroprimoweb.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ramanpan.petroprimoweb.DTO.ActualUserDTO;
import ru.ramanpan.petroprimoweb.DTO.CreateTestDTO;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.model.enums.TestType;
import ru.ramanpan.petroprimoweb.service.TestService;
import ru.ramanpan.petroprimoweb.service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;
    @Value("${upload.path.user}")
    private String uploadPath;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/upload")
    public ResponseEntity.BodyBuilder uploadPicture(@RequestParam("file") MultipartFile file) throws IOException {
        if(file!= null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String path = uploadPath + "/" + file.getOriginalFilename();
            System.out.println(path);
            file.transferTo(new File(path));
        }
        return ResponseEntity.ok();
    }

    @PostMapping("/actUser")
    public ActualUserDTO getActualUser(@RequestBody String nickname) {
        return ActualUserDTO.mapToDTO(userService.findByNickname(nickname));

    }




}