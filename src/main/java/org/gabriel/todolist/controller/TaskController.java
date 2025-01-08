package org.gabriel.todolist.controller;

import lombok.RequiredArgsConstructor;
import org.gabriel.todolist.config.JWTService;
import org.gabriel.todolist.dto.TaskDTO;
import org.gabriel.todolist.dto.TaskPagedResponseDTO;
import org.gabriel.todolist.model.Task;
import org.gabriel.todolist.service.TaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/tasks")
public class TaskController {

    private final JWTService jwtService;
    private final TaskService taskService;

    @PostMapping("/todos")
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO task, @RequestHeader("Authorization") String authHeader) {

        final String token = authHeader.substring(7);  // todo - refactor
        final String userEmail = jwtService.extractEmail(token);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(task, userEmail));

    }


    @GetMapping("/todos/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id, @RequestBody Task task) {

        return null;
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<TaskDTO> update(  // todo - refactor
            @PathVariable Long id,
            @RequestBody TaskDTO dto,
            @RequestHeader("Authorization") String auth) {

        return ResponseEntity.ok(taskService.update(id, dto, auth));

    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestHeader("Authorization") String auth) {

        taskService.delete(id, auth); // todo - refactor

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/todos")
    public ResponseEntity<TaskPagedResponseDTO> searchByPage(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int limit) {

        Pageable pageable = PageRequest.of(page, limit);

        return ResponseEntity.ok(taskService.searchByPage(pageable));

    }
}
