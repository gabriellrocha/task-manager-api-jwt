package org.gabriel.todolist.dto;

import org.gabriel.todolist.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskResponse fromTask(Task task);
    Task fromDtoRequest(TaskRequest request);

}
