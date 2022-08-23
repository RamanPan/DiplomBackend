package ru.ramanpan.petroprimoweb.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ramanpan.petroprimoweb.dto.ActualUserDTO;
import ru.ramanpan.petroprimoweb.dto.AuthenticationRequestDTO;
import ru.ramanpan.petroprimoweb.dto.RegistrationRequestDTO;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.security.JwtTokenProvider;
import ru.ramanpan.petroprimoweb.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class AuthenticationControllerV1 {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider provider;


    @PostMapping("/login")
    public ResponseEntity<ActualUserDTO> authenticate(@RequestBody AuthenticationRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        User user = userService.findByNickname(request.getLogin());
        if (user == null) throw new UsernameNotFoundException("User doesn't exists");
        String token = provider.generateToken(request.getLogin(), user.getRole().name());
        return ResponseEntity.ok(new ActualUserDTO(user.getId(), user.getCreated(), user.getNickname(), user.getFullname(), user.getEmail(), user.getRole().name(),
                user.getDescription(), user.getPicture(), user.getCountPassed(), user.getCountCreated(), token));
    }

    @PostMapping("/register")
    public ResponseEntity<Integer> register(@RequestBody RegistrationRequestDTO request) {
        userService.register(request);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/changePassword")
    public Long changePassword(@RequestBody AuthenticationRequestDTO request) {
        User user = userService.findByNickname(request.getLogin());
        if (user == null) throw new UsernameNotFoundException("User doesn't exists");
        user.setPassword(request.getPassword());
        return userService.changePassword(user);
    }


}

