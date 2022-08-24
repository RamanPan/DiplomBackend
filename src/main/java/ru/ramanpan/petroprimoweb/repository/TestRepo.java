package ru.ramanpan.petroprimoweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ramanpan.petroprimoweb.model.Test;

import java.util.List;
import java.util.Optional;

public interface TestRepo extends JpaRepository<Test, Long> {
    Optional<Test> findByName(String name);

    List<Test> findAllByAuthor(String author);

    @Query("SELECT '*' FROM Test")
    Page<Test> findAllPage(Pageable pageable);
}
