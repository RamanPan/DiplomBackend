package ru.ramanpan.petroprimoweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PetroPrimoWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetroPrimoWebApplication.class, args);
    }

}
