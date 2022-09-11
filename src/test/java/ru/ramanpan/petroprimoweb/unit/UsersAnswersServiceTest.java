package ru.ramanpan.petroprimoweb.unit;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ramanpan.petroprimoweb.service.UsersAnswersService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersAnswersServiceTest {
    @Autowired
    private UsersAnswersService usersAnswersService;

}
