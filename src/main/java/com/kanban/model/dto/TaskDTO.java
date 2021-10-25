package com.kanban.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TaskDTO {

    private String title;
    private String description;
    private String status;
    private String taskColor;
    private String deadline;
}
