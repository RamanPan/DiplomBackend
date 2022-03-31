package ru.ramanpan.petroprimoweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ramanpan.petroprimoweb.model.Answer;

public interface AnswerRepo extends JpaRepository<Answer,Long> {
}
