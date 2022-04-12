package ru.ramanpan.petroprimoweb.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ramanpan.petroprimoweb.DTO.*;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.model.enums.TestType;
import ru.ramanpan.petroprimoweb.service.TestService;
import ru.ramanpan.petroprimoweb.service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @Value("${upload.path.user}")
    private String uploadPath;

    public UserRestController(UserService userService, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
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

    @PostMapping("/setPicture")
    public ResponseEntity.BodyBuilder setPicture(@RequestBody UploadUserDTO userDTO) {
        User user = userService.findById(userDTO.getId());
        user.setPicture(userDTO.getPicture());
        userService.save(user);
        return ResponseEntity.ok();
    }
    @PostMapping("/getPicture")
    public Map<String,String> getPicture(@RequestBody GetPictureDTO dto) {
        Map<String,String> map = new HashMap<>();
        map.put("picture",userService.findByNickname(dto.getAuthor()).getPicture());
        return map;

    }

    @PostMapping("/setLogin")
    public String setLogin(@RequestBody UploadUserDTO userDTO) {
        User user = userService.findById(userDTO.getId());
        user.setNickname(userDTO.getLogin());
        return userService.save(user).getNickname();
    }
    @PostMapping("/setEmail")
    public String setEmail(@RequestBody UploadUserDTO userDTO) {
        User user = userService.findById(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        return userService.save(user).getEmail();
    }
    @PostMapping("/setFullname")
    public String setFullname(@RequestBody UploadUserDTO userDTO) {
        User user = userService.findById(userDTO.getId());
        user.setFullname(userDTO.getFullname());
        return userService.save(user).getFullname();
    }
    @PostMapping("/setPassword")
    public void setPassword(@RequestBody UploadUserDTO userDTO) {
        User user = userService.findById(userDTO.getId());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        userService.save(user);
    }

    @PostMapping("/actUser")
    public ActualUserDTO getActualUser(@RequestBody UploadUserDTO userDTO) {
        return ActualUserDTO.mapToDTO(userService.findById(userDTO.getId()));

    }




}