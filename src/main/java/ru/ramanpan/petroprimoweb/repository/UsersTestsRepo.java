package ru.ramanpan.petroprimoweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ramanpan.petroprimoweb.model.Test;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.model.UsersTests;

import java.util.List;

public interface UsersTestsRepo extends JpaRepository<UsersTests, Long> {
    List<UsersTests> findAllByUser(User user);

    List<UsersTests> findAllByTest(Test test);
}
