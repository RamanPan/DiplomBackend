package ru.ramanpan.petroprimoweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ramanpan.petroprimoweb.model.Question;

public interface QuestionRepo extends JpaRepository<Question,Long> {
}
