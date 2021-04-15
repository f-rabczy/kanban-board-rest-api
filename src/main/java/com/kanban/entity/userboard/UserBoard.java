package com.kanban.entity.userboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanban.entity.user.UserDAO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_board")
public class UserBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "userBoard", cascade = CascadeType.ALL)
    private List<UserBoardColumn> columns = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private UserDAO user;

    public void addColumn(UserBoardColumn column) {
        this.columns.add(column);
        column.setUserBoard(this);
    }

    public void addColumnWithTasks(UserBoardColumn column) {
        for (UserTask userTask : column.getUserTaskList()) {
            userTask.setColumn(column);
        }
        this.columns.add(column);
        column.setUserBoard(this);
    }


    public UserDAO getUser() {
        return user;
    }

    public void setUser(UserDAO user) {
        this.user = user;
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

    public List<UserBoardColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<UserBoardColumn> columns) {
        this.columns = columns;
    }
}
