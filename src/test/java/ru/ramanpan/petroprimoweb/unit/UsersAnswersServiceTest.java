package ru.ramanpan.petroprimoweb.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ramanpan.petroprimoweb.dto.UsersAnswersDTO;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.model.UsersTests;
import ru.ramanpan.petroprimoweb.repository.UsersAnswersRepo;
import ru.ramanpan.petroprimoweb.service.QuestionService;
import ru.ramanpan.petroprimoweb.service.UserService;
import ru.ramanpan.petroprimoweb.service.UsersAnswersService;
import ru.ramanpan.petroprimoweb.service.UsersTestsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersAnswersServiceTest {
    @Autowired
    private UsersAnswersService usersAnswersService;
    @MockBean
    private UsersAnswersRepo usersAnswersRepo;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private UsersTestsService usersTestsService;
    @MockBean
    private UserService userService;

    @Test
    public void saveTest() {
        UsersAnswersDTO userAnswerDTO = new UsersAnswersDTO();
        usersAnswersService.save(userAnswerDTO);
        Mockito.verify(questionService, Mockito.times(2)).findById(userAnswerDTO.getQuestion());
        Mockito.verify(usersTestsService, Mockito.times(1)).findById(userAnswerDTO.getUserTest());
        Mockito.verify(userService, Mockito.times(1)).findById(userAnswerDTO.getUser());
    }

    @Test
    public void findAllByUserAndTest() {
        User user = new User();
        UsersTests usersTests = new UsersTests();
        usersAnswersService.findAllByUserAndTest(user,usersTests);
        Mockito.verify(usersAnswersRepo, Mockito.times(1)).findAllByUserAndTest(user,usersTests);
    }

    @Test
    public void deleteByIdTest() {
        Long id = 1L;
        usersAnswersService.deleteById(id);
        Mockito.verify(usersAnswersRepo, Mockito.times(1)).deleteById(id);
    }

}
