package ru.ramanpan.petroprimoweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AnswerRepo extends JpaRepository<Answer,Long> {
    Optional<Answer> findByStatement(String statement);
    void deleteAllByQuestion(Question question);
    Optional<List<Answer>> findAllByQuestion(Question question);
    Optional<List<Answer>> findAllByQuestionAndCorrectness(Question question,boolean correctness);
}
