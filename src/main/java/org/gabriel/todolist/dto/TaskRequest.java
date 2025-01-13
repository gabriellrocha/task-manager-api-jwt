package org.gabriel.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.gabriel.todolist.enums.Priority;
import org.gabriel.todolist.enums.Status;

public record TaskRequest(

        @NotBlank(message = "Title is mandatory")
        String title,

        @NotBlank(message = "Description is mandatory")
        String description,

        @NotNull(message = "Priority is mandatory")
        Priority priority,

        @NotNull(message = "Status is mandatory")
        Status status
) {

}
