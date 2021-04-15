package com.kanban.entity.project;



import com.kanban.entity.user.UserDAO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.kanban.model.util.DateUtil.dateConverter;

@Entity
public class Project {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    private List<UserDAO> users = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private ProjectBoard board;

    @OneToOne
    private UserDAO creator;

    @Column(nullable = false)
    private String date;

    public void addBoard(ProjectBoard projectBoard){
        this.board = projectBoard;
        projectBoard.setProject(this);
    }

    public void addUser(UserDAO user){
        this.users.add(user);
        user.addProject(this);
    }


    public Long getId() {
        return id;
    }


    public Project(){
        this.date = dateConverter();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserDAO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDAO> users) {
        this.users = users;
    }

    public ProjectBoard getBoard() {
        return board;
    }

    public void setBoard(ProjectBoard board) {
        this.board = board;
    }

    public UserDAO getCreator() {
        return creator;
    }

    public void setCreator(UserDAO creator) {
        this.creator = creator;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                '}';
    }
}
