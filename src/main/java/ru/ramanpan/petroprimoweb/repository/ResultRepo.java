package ru.ramanpan.petroprimoweb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.ramanpan.petroprimoweb.model.Result;
import ru.ramanpan.petroprimoweb.model.Test;

import java.util.List;
import java.util.Optional;

public interface ResultRepo extends JpaRepository<Result, Long> {
    List<Result> findAllByTest(Test test);

    Optional<Result> findByDescription(String description);

    Optional<Result> findByHeader(String header);
}
