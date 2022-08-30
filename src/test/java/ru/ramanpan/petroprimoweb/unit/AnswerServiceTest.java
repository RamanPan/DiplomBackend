package ru.ramanpan.petroprimoweb.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ramanpan.petroprimoweb.dto.AnswerDTO;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.repository.AnswerRepo;
import ru.ramanpan.petroprimoweb.service.AnswerService;
import ru.ramanpan.petroprimoweb.service.QuestionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnswerServiceTest {
    @Autowired
    private AnswerService answerService;
    @MockBean
    private AnswerRepo answerRepo;
    @MockBean
    private QuestionService questionService;

    @Test
    public void findAllByQuestionAndCorrectnessTest() {
        Question question = new Question();
        boolean correctness = true;
        answerService.findAllByQuestionAndCorrectness(question, correctness);
        Mockito.verify(answerRepo, Mockito.times(1)).findAllByQuestionAndCorrectness(question, true);
    }

    @Test
    public void saveTest() {
        AnswerDTO answerDTO = new AnswerDTO();
        answerService.save(answerDTO);
        Mockito.verify(questionService,Mockito.times(1)).findById(answerDTO.getId());
    }

    @Test
    public void deleteByIdTest() {
        Long id = 1L;
        answerService.deleteById(id);
        Mockito.verify(answerRepo,Mockito.times(1)).deleteById(id);
    }
}
