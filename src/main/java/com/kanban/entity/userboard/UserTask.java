package com.kanban.entity.userboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanban.model.enums.TaskColor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

import static com.kanban.model.util.DateUtil.dateConverter;

@Entity
@Getter
@Setter
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

    public void setOrder(int order) {
        this.listOrder = order;
    }

    @Override
    public int compareTo(@NotNull UserTask o) {
        return Integer.compare(this.listOrder, o.listOrder);
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


}
