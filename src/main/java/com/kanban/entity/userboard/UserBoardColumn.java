package com.kanban.entity.userboard;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_board_column")
public class UserBoardColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "board_id" )
    @JsonIgnore
    private UserBoard userBoard;

    @OneToMany(mappedBy = "column", cascade = {CascadeType.ALL})
    private List<UserTask> userTaskList = new ArrayList<>();

    public UserBoardColumn() {

    }

    public UserBoardColumn(String name){
        this.name=name;
    }

    public void addTask(UserTask userTask){
        this.userTaskList.add(userTask);
        userTask.setColumn(this);
        if(userTaskList.size()>1){
            userTask.setOrder(userTaskList.size() - 1);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserBoard getUserBoard() {
        return userBoard;
    }

    public void setUserBoard(UserBoard userBoard) {
        this.userBoard = userBoard;
    }

    public List<UserTask> getUserTaskList() {
        return userTaskList;
    }

    public void setUserTaskList(List<UserTask> userTaskList) {
        this.userTaskList = userTaskList;
    }
}

