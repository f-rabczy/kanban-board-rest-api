package com.kanban.entity.userboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanban.entity.user.UserDAO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_board")
@Getter
@Setter
@NoArgsConstructor
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

}
