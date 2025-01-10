package org.gabriel.todolist.repository;

import org.gabriel.todolist.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByUserEmail(String email, Pageable pageable);
}
