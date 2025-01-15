package org.gabriel.todolist.service;

import lombok.RequiredArgsConstructor;
import org.gabriel.todolist.dto.TaskRequest;
import org.gabriel.todolist.dto.TaskResponse;
import org.gabriel.todolist.dto.TaskMapper;
import org.gabriel.todolist.dto.TaskPaged;
import org.gabriel.todolist.exception.ResourceNotFoundException;
import org.gabriel.todolist.model.Task;
import org.gabriel.todolist.model.User;
import org.gabriel.todolist.repository.TaskRepository;
import org.gabriel.todolist.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;
    private final TaskMapper taskMapper;

    public TaskResponse create(TaskRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found for email: " + email
                ));

        Task task = taskMapper.fromDtoRequest(request);
        task.setUser(user);

        return taskMapper.fromTask(repository.save(task));
    }

    public TaskResponse getTaskById(Long id) {

        Task task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task with id [%s] not found".formatted(id)));

        authorizationService.checkAuthorization(task);

        return taskMapper.fromTask(task);

    }

    public TaskResponse update(Long id, TaskRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found for email: " + email
                ));

        Task task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task with id [%s] not found".formatted(id)));

        authorizationService.checkAuthorization(task);

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setPriority(request.priority());
        task.setStatus(request.status());
        task.setUser(user);

        return taskMapper.fromTask(repository.save(task));

    }

    public void delete(Long id) {

        Task task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task with id [%s] not found".formatted(id)));

        authorizationService.checkAuthorization(task);

        repository.delete(task);

    }

    public TaskPaged searchByPage(String priority, String status, Pageable pageable) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        TaskSpecification spec = new TaskSpecification(priority, status, email);

        Page<Task> page = repository.findAll(spec, pageable);

        List<TaskResponse> list = page
                .getContent()
                .stream()
                .map(taskToTaskDto())
                .toList();


        return TaskPaged.builder()
                .tasks(list)
                .page(page.getNumber())
                .limit(page.getSize())
                .total(page.getTotalElements())
                .build();
    }


    private Function<Task, TaskResponse> taskToTaskDto() {

        return task -> new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getPriority(),
                task.getStatus()
        );
    }
}
