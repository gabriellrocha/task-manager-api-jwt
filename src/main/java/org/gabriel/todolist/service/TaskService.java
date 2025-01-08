package org.gabriel.todolist.service;

import org.gabriel.todolist.config.JWTService;
import org.gabriel.todolist.dto.TaskDTO;
import org.gabriel.todolist.exception.AccessDeniedException;
import org.gabriel.todolist.exception.TaskNotFoundException;
import org.gabriel.todolist.exception.UserNotFoundException;
import org.gabriel.todolist.model.Task;
import org.gabriel.todolist.model.User;
import org.gabriel.todolist.repository.TaskRepository;
import org.gabriel.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final JWTService jwtService;

    @Autowired
    public TaskService(UserRepository userRepository, TaskRepository taskRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.jwtService = jwtService;
    }

    public TaskDTO create(TaskDTO dto, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));


        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .user(user)
                .build();

        var taskSave = taskRepository.save(task);

        return new TaskDTO(taskSave.getId(), taskSave.getTitle(), taskSave.getDescription());
    }

    public TaskDTO update(Long id, TaskDTO dto, String auth) {

        final String userEmail = jwtService.extractEmail(auth.substring(7));

        Long userId = userRepository.findUserIdByEmail(userEmail);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task Not Found"));

        if(!task.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized user"); // todo - tratar 403
        }


        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());

        taskRepository.save(task);

        return new TaskDTO(task.getId(), task.getTitle(), task.getDescription());
    }

    public void delete(Long id, String auth) {

        final String userEmail = jwtService.extractEmail(auth.substring(7));

//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName()); // todo refactor

        Long userId = userRepository.findUserIdByEmail(userEmail);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task Not Found"));

        if(!task.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized user"); // todo - tratar 403
        }

        taskRepository.delete(task);

    }
}
