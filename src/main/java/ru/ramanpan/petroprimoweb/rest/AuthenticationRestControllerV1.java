package ru.ramanpan.petroprimoweb.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
public class AuthenticationRestControllerV1 {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider provider;

    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, UserServiceImpl userService, JwtTokenProvider provider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.provider = provider;
    }

//    @PostMapping("/log")
//    public ResponseEntity.BodyBuilder authenticate(@RequestBody AuthenticationRequestDTO request) {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
//            System.out.println("here");
//            User user = userService.findByNickname(request.getLogin());
//            if(user == null) throw new UsernameNotFoundException("User doesn't exists");
//            String token = provider.generateToken(request.getLogin(), user.getRole().name());
//            Map<Object, Object> response = new HashMap<>();
////            response.put("nickname", request.getNickname());
//            response.put("id",user.getId());
//            response.put("date_register",user.getCreated());
//            response.put("nickname",user.getNickname());
//            response.put("fullname",user.getFullname());
//            response.put("email",user.getEmail());
//            response.put("role",user.getRole());
//            response.put("description",user.getDescription());
//            response.put("picture",user.getPicture());
//            response.put("token", token);
//            return (ResponseEntity.BodyBuilder) ResponseEntity.ok();
//    }
    @PostMapping("/login")
    public ActualUserDTO authenticate(@RequestBody AuthenticationRequestDTO request) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
            User user = userService.findByNickname(request.getLogin());
            if(user == null) throw new UsernameNotFoundException("User doesn't exists");
            String token = provider.generateToken(request.getLogin(), user.getRole().name());
            System.out.println("auth");
            return new ActualUserDTO(user.getId(),user.getCreated(),user.getNickname(),user.getFullname(),user.getEmail(),user.getRole().name(),user.getDescription(),user.getPicture(), user.getCountPassed(), user.getCountCreated(), token);
        }
    @PostMapping("/register")
    public Integer register(@RequestBody RegistrationRequestDTO request) {
        User user = new User();
        user.setNickname(request.getNickname());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFullname(request.getFullname());
        user.setCountPassed(0);
        user.setCountCreated(0);
        if(request.getRole().equals("Преподаватель")) user.setRole(Role.ROLE_PROFESSOR);
        else user.setRole(Role.ROLE_STUDENT);
        userService.register(user);
        return 1;
    }
    @PostMapping("/changePassword")
    public Long changePassword(@RequestBody AuthenticationRequestDTO request) {
        User user = userService.findByNickname(request.getLogin());
        if(user == null) throw new UsernameNotFoundException("User doesn't exists");
        user.setPassword(request.getPassword());
        return userService.changePassword(user);
    }

    @RequestMapping("/logout")
    public Integer logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request,response,null);
        System.out.println("exit");
        return 1;
    }

}

