package org.gabriel.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskPagedResponseDTO {

    private List<TaskDTO> data;
    private int page;
    private int limit;
    private long total;

}
