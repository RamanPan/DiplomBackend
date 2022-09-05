package ru.ramanpan.petroprimoweb.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ramanpan.petroprimoweb.dto.ResultDTO;
import ru.ramanpan.petroprimoweb.repository.ResultRepo;
import ru.ramanpan.petroprimoweb.service.ResultService;
import ru.ramanpan.petroprimoweb.service.TestService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResultServiceTest {
    @Autowired
    private ResultService resultService;
    @MockBean
    private ResultRepo resultRepo;
    @MockBean
    private TestService testService;

    @Test
    public void saveTest() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setStartCondition(0);
        resultDTO.setEndCondition(0);
        resultService.save(resultDTO);
        Mockito.verify(testService, Mockito.times(1)).findById(resultDTO.getId());
    }

    @Test
    public void findAllTest() {
        resultService.findAll();
        Mockito.verify(resultRepo, Mockito.times(1)).findAll();
    }

    @Test
    public void deleteByIdTest() {
        Long id = 1L;
        resultService.deleteById(id);
        Mockito.verify(resultRepo, Mockito.times(1)).deleteById(id);
    }
}
