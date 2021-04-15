package com.kanban.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
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



    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProjectBoardColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<ProjectBoardColumn> columns) {
        this.columns = columns;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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
