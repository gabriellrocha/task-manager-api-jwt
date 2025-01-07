package org.gabriel.todolist.controller;

import org.gabriel.todolist.config.JWTService;
import org.gabriel.todolist.dto.TaskDTO;
import org.gabriel.todolist.model.Task;
import org.gabriel.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tasks")
public class TaskController {

    private final JWTService jwtService;
    private final TaskService taskService;

    @Autowired
    public TaskController(JWTService jwtService, TaskService taskService) {
        this.jwtService = jwtService;
        this.taskService = taskService;
    }

    @PostMapping("/todos")
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO task, @RequestHeader("Authorization") String authHeader) {

        final String token = authHeader.substring(7);
        final String userEmail = jwtService.extractEmail(token);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(task, userEmail));

    }

    @GetMapping("/todos")
    public ResponseEntity<?> findAll() {

        return null;
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id, @RequestBody Task task) {

        return null;
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> update() {

        return null;
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        return null;
    }


}
