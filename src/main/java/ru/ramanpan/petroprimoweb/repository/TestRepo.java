package ru.ramanpan.petroprimoweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ramanpan.petroprimoweb.model.Test;

import java.util.List;
import java.util.Optional;

public interface TestRepo extends JpaRepository<Test, Long> {
    Optional<Test> findByName(String name);

    Optional<List<Test>> findAllByAuthor(String author);
}
