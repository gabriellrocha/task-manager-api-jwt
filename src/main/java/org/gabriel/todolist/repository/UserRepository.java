package org.gabriel.todolist.repository;

import org.gabriel.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u.id FROM users u WHERE u.email = :email")
    Optional<Long> findUserIdByEmail(@Param("email") String email);
}
