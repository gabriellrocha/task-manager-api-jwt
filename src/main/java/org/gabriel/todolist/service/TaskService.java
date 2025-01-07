package org.gabriel.todolist.service;

import org.gabriel.todolist.dto.TaskDTO;
import org.gabriel.todolist.exception.UserNotFoundException;
import org.gabriel.todolist.model.Task;
import org.gabriel.todolist.model.User;
import org.gabriel.todolist.repository.TaskRepository;
import org.gabriel.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
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

}
