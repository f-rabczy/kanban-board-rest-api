package com.kanban.entity.project;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "project_board_column")
public class ProjectBoardColumn {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "board_id" )
    @JsonIgnore
    private ProjectBoard projectBoard;

    @OneToMany(mappedBy = "column", cascade = {CascadeType.ALL})
    private List<ProjectTask> projectTaskList = new ArrayList<>();

    public ProjectBoardColumn() {

    }

    public ProjectBoardColumn(String name){
        this.name=name;
    }

    public void addTask(ProjectTask projectTask){
        this.projectTaskList.add(projectTask);
        projectTask.setColumn(this);
        if(projectTaskList.size()>1){
            projectTask.setOrder(projectTaskList.size() - 1);
        }
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

    public ProjectBoard getProjectBoard() {
        return projectBoard;
    }

    public void setProjectBoard(ProjectBoard projectBoard) {
        this.projectBoard = projectBoard;
    }

    public List<ProjectTask> getProjectTaskList() {
        return projectTaskList;
    }

    public void setProjectTaskList(List<ProjectTask> projectTaskList) {
        this.projectTaskList = projectTaskList;
    }
}

