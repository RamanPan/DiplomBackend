package ru.ramanpan.petroprimoweb.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Hello {
    @GetMapping("/student")
    public String helloStudent() {
        return "Student!!!";
    }
    @GetMapping("/professor")
    @PreAuthorize("hasRole('ROLE_PROFESSOR')")
    public String helloProfessor() {
        return "Professor!!!";
    }

}
