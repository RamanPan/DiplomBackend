package ru.ramanpan.petroprimoweb.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.ramanpan.petroprimoweb.DTO.ActualUserDTO;
import ru.ramanpan.petroprimoweb.DTO.AuthenticationRequestDTO;
import ru.ramanpan.petroprimoweb.DTO.RegistrationRequestDTO;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.model.enums.Role;
import ru.ramanpan.petroprimoweb.security.JwtTokenProvider;
import ru.ramanpan.petroprimoweb.service.UserService;
import ru.ramanpan.petroprimoweb.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationControllerV1 {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider provider;

    public AuthenticationControllerV1(AuthenticationManager authenticationManager, UserServiceImpl userService, JwtTokenProvider provider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.provider = provider;
    }

    @PostMapping("/login")
    public ResponseEntity<ActualUserDTO> authenticate(@RequestBody AuthenticationRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        User user = userService.findByNickname(request.getLogin());
        if (user == null) throw new UsernameNotFoundException("User doesn't exists");
        String token = provider.generateToken(request.getLogin(), user.getRole().name());
        System.out.println("auth");
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

