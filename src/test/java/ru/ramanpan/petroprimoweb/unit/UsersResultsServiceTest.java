package ru.ramanpan.petroprimoweb.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.repository.UsersResultsRepo;
import ru.ramanpan.petroprimoweb.service.UsersResultsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersResultsServiceTest {
    @Autowired
    private UsersResultsService usersResultsService;
    @MockBean
    private UsersResultsRepo usersResultsRepo;

    @Test
    public void findResultByUserTest() {
        User user = new User();
        usersResultsService.findResultByUser(user);
        Mockito.verify(usersResultsRepo,Mockito.times(1)).findAllByUser(user);
    }
    @Test
    public void deleteByIdTest() {
        Long id = 1L;
        usersResultsService.deleteById(id);
        Mockito.verify(usersResultsRepo,Mockito.times(1)).deleteById(id);
    }
}
