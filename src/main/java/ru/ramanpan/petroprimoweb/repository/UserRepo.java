package ru.ramanpan.petroprimoweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ramanpan.petroprimoweb.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String value);

    Optional<User> findByNicknameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    Boolean existsByNickname(String username);

    Boolean existsByEmail(String email);
}
