package ru.ramanpan.petroprimoweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ramanpan.petroprimoweb.model.Result;
import ru.ramanpan.petroprimoweb.model.User;
import ru.ramanpan.petroprimoweb.model.UsersResults;

import java.util.List;
import java.util.Optional;

public interface UsersResultsRepo extends JpaRepository<UsersResults,Long> {
    Optional<List<UsersResults>> findAllByUser(User user);
    Optional<List<UsersResults>> findAllByResult(Result result);
 }
