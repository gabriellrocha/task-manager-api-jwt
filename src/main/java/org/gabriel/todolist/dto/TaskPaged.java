package org.gabriel.todolist.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TaskPaged {

    private List<TaskResponse> tasks;
    private Integer page;
    private Integer limit;
    private Long total;

}

