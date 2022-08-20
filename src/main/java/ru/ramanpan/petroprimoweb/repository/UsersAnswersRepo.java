package ru.ramanpan.petroprimoweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ramanpan.petroprimoweb.model.Question;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.model.UsersAnswers;
import ru.ramanpan.petroprimoweb.model.UsersTests;

import java.util.List;

public interface UsersAnswersRepo extends JpaRepository<UsersAnswers, Long> {
    List<UsersAnswers> findAllByUser(User user);

    List<UsersAnswers> findAllByAnswer(String answer);

    List<UsersAnswers> findAllByQuestion(Question question);

    List<UsersAnswers> findAllByTest(UsersTests usersTests);

    List<UsersAnswers> findAllByUserAndTest(User user, UsersTests usersTests);
}
