package org.gabriel.todolist.service;

import lombok.RequiredArgsConstructor;
import org.gabriel.todolist.dto.TaskDTO;
import org.gabriel.todolist.dto.TaskPagedResponseDTO;
import org.gabriel.todolist.exception.TaskNotFoundException;
import org.gabriel.todolist.model.Task;
import org.gabriel.todolist.model.User;
import org.gabriel.todolist.repository.TaskRepository;
import org.gabriel.todolist.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final AuthorizationService authorizationService;

    public TaskDTO create(TaskDTO dto) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow();


        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .user(user)
                .build();

        Task taskSaved = taskRepository.save(task);

        return new TaskDTO(taskSaved.getId(), taskSaved.getTitle(), taskSaved.getDescription());
    }

    public TaskDTO update(Long id, TaskDTO dto) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task Not Found"));

        authorizationService.checkAuthorization(task);

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());

        taskRepository.save(task);

        return new TaskDTO(task.getId(), task.getTitle(), task.getDescription());
    }

    public void delete(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task Not Found"));

        authorizationService.checkAuthorization(task);

        taskRepository.delete(task);

    }

    public TaskPagedResponseDTO searchByPage(Pageable pageable) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Page<Task> taskPage = taskRepository.findByUserEmail(email, pageable);

        List<TaskDTO> list = taskPage
                .getContent()
                .stream()
                .map(task -> new TaskDTO(task.getId(), task.getTitle(), task.getDescription()))
                .toList();

        return new TaskPagedResponseDTO(
                list, taskPage.getNumber(), taskPage.getSize(), taskPage.getTotalElements());

    }

}
