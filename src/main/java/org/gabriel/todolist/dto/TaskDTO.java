package org.gabriel.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TaskDTO {

    private final Long id;
    private final String title;
    private final String description;

}
