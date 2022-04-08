package ru.ramanpan.petroprimoweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ramanpan.petroprimoweb.model.Answer;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.Test;

import java.util.List;
import java.util.Optional;

public interface AnswerRepo extends JpaRepository<Answer,Long> {
    Optional<List<Answer>> findAllByQuestion(Question question);
    Optional<Answer> findByStatement(String statement);
}
