package org.gabriel.todolist.controller;

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
    public ResponseEntity<TaskResponse> create(@RequestBody TaskRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(request));

    }

    @GetMapping("tasks/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id, @RequestBody Task task) { // todo - implementar

        return null;
    }

    @PutMapping("tasks/{id}")
    public ResponseEntity<TaskResponse> update(
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
    public ResponseEntity<TaskPaged> searchByPage(@PageableDefault(sort = "title") Pageable pageable) {

        return ResponseEntity.ok(taskService.searchByPage(pageable));

    }
}
