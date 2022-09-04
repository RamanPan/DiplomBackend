package ru.ramanpan.petroprimoweb.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ramanpan.petroprimoweb.dto.QuestionDTO;
import ru.ramanpan.petroprimoweb.repository.QuestionRepo;
import ru.ramanpan.petroprimoweb.service.AnswerService;
import ru.ramanpan.petroprimoweb.service.QuestionService;
import ru.ramanpan.petroprimoweb.service.TestService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceTest {
    @Autowired
    public QuestionService questionService;
    @MockBean
    private QuestionRepo questionRepo;
    @MockBean
    private TestService testService;
    @MockBean
    private AnswerService answerService;

    @Test
    public void saveTest() {
        QuestionDTO questionDTO = new QuestionDTO();
        questionService.save(questionDTO);
        Mockito.verify(testService, Mockito.times(1)).findById(questionDTO.getId());
    }

    @Test
    public void deleteByIdTest() {
        Long id = 1L;
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(id);
//        questionDTO.setPicture(" ");
//        questionDTO.setType("Закрытый");
        questionService.save(questionDTO);
        Mockito.verify(testService, Mockito.times(1)).findById(id);
    }
}
