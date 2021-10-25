package com.kanban.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProjectBoard {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "projectBoard", cascade = CascadeType.ALL) //
    private List<ProjectBoardColumn> columns = new ArrayList<>();

    @OneToOne(mappedBy = "board")
    @JsonIgnore
    private Project project;

    public void addColumn(ProjectBoardColumn column) {
        this.columns.add(column);
        column.setProjectBoard(this);
    }

    public void addColumnWithTasks(ProjectBoardColumn column) {
        for (ProjectTask projectTask : column.getProjectTaskList()) {
            projectTask.setColumn(column);
        }
        this.columns.add(column);
        column.setProjectBoard(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectBoard that = (ProjectBoard) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getColumns(), that.getColumns()) &&
                Objects.equals(getProject(), that.getProject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getColumns(), getProject());
    }
}
