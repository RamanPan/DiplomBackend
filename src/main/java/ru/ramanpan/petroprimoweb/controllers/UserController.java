package ru.ramanpan.petroprimoweb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ramanpan.petroprimoweb.dto.ActualUserDTO;
import ru.ramanpan.petroprimoweb.dto.UploadUserDTO;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @Value("${upload.path.user}")
    private String uploadPath;


    @PostMapping("/upload")
    public ResponseEntity.BodyBuilder uploadPicture(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String path = String.format("%s/%s", uploadPath, file.getOriginalFilename());
            file.transferTo(new File(path));
        }
        return ResponseEntity.ok();
    }

    @PostMapping("/setPicture")
    public ResponseEntity.BodyBuilder setPicture(@RequestBody UploadUserDTO userDTO) {
        User user = userService.findById(userDTO.getId());
        user.setPicture(userDTO.getPicture());
        userService.save(user);
        return ResponseEntity.ok();
    }

    @GetMapping("/getPicture/{author}")
    public ResponseEntity<Map<String, String>> getPicture(@PathVariable("author") String author) {
        Map<String, String> map = new HashMap<>();
        map.put("picture", userService.findByNickname(author).getPicture());
        return ResponseEntity.ok(map);

    }

    @PostMapping("/setLogin")
    public ResponseEntity<String> setLogin(@RequestBody UploadUserDTO userDTO) {
        User user = userService.findById(userDTO.getId());
        user.setNickname(userDTO.getLogin());
        return ResponseEntity.ok(userService.save(user).getNickname());
    }

    @PostMapping("/setEmail")
    public ResponseEntity<String> setEmail(@RequestBody UploadUserDTO userDTO) {
        User user = userService.findById(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        return ResponseEntity.ok(userService.save(user).getEmail());
    }

    @PostMapping("/setFullname")
    public ResponseEntity<String> setFullname(@RequestBody UploadUserDTO userDTO) {
        User user = userService.findById(userDTO.getId());
        user.setFullname(userDTO.getFullname());
        return ResponseEntity.ok(userService.save(user).getFullname());
    }

    @PostMapping("/setPassword")
    public ResponseEntity.BodyBuilder setPassword(@RequestBody UploadUserDTO userDTO) {
        User user = userService.findById(userDTO.getId());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        userService.save(user);
        return ResponseEntity.ok();
    }

    @GetMapping("/actUser/{id}")
    public ResponseEntity<ActualUserDTO> getActualUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ActualUserDTO.mapToDTO(userService.findById(id)));

    }


}