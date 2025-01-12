package org.gabriel.todolist.dto;

import org.gabriel.todolist.enums.Priority;
import org.gabriel.todolist.enums.Status;

import java.time.LocalDateTime;


public record TaskResponse(
        Long id,
        String title,
        String description,
        LocalDateTime createdAt,
        Priority priority,
        Status status
) {

}
