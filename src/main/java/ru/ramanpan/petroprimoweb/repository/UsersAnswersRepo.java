package ru.ramanpan.petroprimoweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ramanpan.petroprimoweb.model.UsersAnswers;

public interface UsersAnswersRepo extends JpaRepository<UsersAnswers,Long> {
}
