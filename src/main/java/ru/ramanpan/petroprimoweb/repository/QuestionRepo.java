package ru.ramanpan.petroprimoweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.Test;

import java.util.List;
import java.util.Optional;

public interface QuestionRepo extends JpaRepository<Question,Long> {
    Optional<List<Question>> findAllByTest(Test test);
    Optional<Question> findByStatement(String statement);
}
