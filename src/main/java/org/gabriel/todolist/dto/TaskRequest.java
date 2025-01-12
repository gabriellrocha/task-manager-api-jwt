package org.gabriel.todolist.dto;

import org.gabriel.todolist.enums.Priority;
import org.gabriel.todolist.enums.Status;

public record TaskRequest(
        String title,
        String description,
        Priority priority,
        Status status
) {

}
