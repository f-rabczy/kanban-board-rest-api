package com.kanban.entity.project;



import com.kanban.entity.user.UserDAO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.kanban.model.util.DateUtil.dateConverter;

@Entity
@NoArgsConstructor
@Getter
@Setter
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

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                '}';
    }
}
