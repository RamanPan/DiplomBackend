package ru.ramanpan.petroprimoweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.model.UsersAnswers;
import ru.ramanpan.petroprimoweb.model.UsersTests;

import java.util.List;
import java.util.Optional;

public interface UsersAnswersRepo extends JpaRepository<UsersAnswers, Long> {
    Optional<List<UsersAnswers>> findAllByUser(User user);

    Optional<List<UsersAnswers>> findAllByAnswer(String answer);

    Optional<List<UsersAnswers>> findAllByQuestion(Question question);

    Optional<List<UsersAnswers>> findAllByTest(UsersTests usersTests);

    Optional<List<UsersAnswers>> findAllByUserAndTest(User user, UsersTests usersTests);
}
