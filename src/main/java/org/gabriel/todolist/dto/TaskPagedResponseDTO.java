package org.gabriel.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TaskPagedResponseDTO {

    private List<TaskDTO> data;
    private final int page;
    private final int limit;
    private final long total;

}
