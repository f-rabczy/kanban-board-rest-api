package com.kanban.model.dto;

public class TaskDTO {
    private String title;
    private String description;
    private String status;
    private String taskColor;
    private String deadline;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getTaskColor() {
        return taskColor;
    }

    public String getDeadline() {
        return deadline;
    }
}
