package com.kanban.entity.userboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanban.model.enums.TaskColor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import static com.kanban.model.util.DateUtil.dateConverter;

@Entity
@Table(name = "user_task")
public class UserTask implements Comparable<UserTask> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String date;

    @Column
    private Integer listOrder = 0;

    @Column
    @Enumerated(EnumType.STRING)
    private TaskColor taskColor;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "column_id")
    @JsonIgnore
    private UserBoardColumn column;

    public UserTask() {
        this.date = dateConverter();
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UserBoardColumn getColumn() {
        return column;
    }

    public void setColumn(UserBoardColumn column) {
        this.column = column;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public int getOrder() {
        return listOrder;
    }

    public void setOrder(int order) {
        this.listOrder = order;
    }

    public TaskColor getTaskColor() {
        return taskColor;
    }

    public void setTaskColor(TaskColor taskColor) {
        this.taskColor = taskColor;
    }

    @Override
    public int compareTo(@NotNull UserTask o) {
        return Integer.compare(this.listOrder,o.listOrder);
    }
}
