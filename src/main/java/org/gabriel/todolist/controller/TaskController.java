package org.gabriel.todolist.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gabriel.todolist.dto.TaskRequest;
import org.gabriel.todolist.dto.TaskResponse;
import org.gabriel.todolist.dto.TaskPaged;
import org.gabriel.todolist.model.Task;
import org.gabriel.todolist.service.TaskService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("tasks")
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(request));

    }

    @GetMapping("tasks/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("tasks/{id}")
    public ResponseEntity<TaskResponse> update(
            @Valid
            @PathVariable Long id,
            @RequestBody TaskRequest request) {

        return ResponseEntity.ok(taskService.update(id, request));

    }

    @DeleteMapping("tasks/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        taskService.delete(id);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/tasks")
    public ResponseEntity<TaskPaged> searchByPage(
            @RequestParam(defaultValue = "HIGH") String priority,
            @RequestParam(defaultValue = "PENDING") String status,
            @PageableDefault(sort = "createdAt") Pageable pageable
    ) {


        TaskPaged taskPaged = taskService.searchByPage(priority, status, pageable);

        return ResponseEntity.ok(taskPaged);

    }
}
