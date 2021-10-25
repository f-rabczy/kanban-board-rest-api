package com.kanban.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanban.entity.project.Project;
import com.kanban.entity.project.ProjectInvitation;
import com.kanban.entity.project.ProjectTaskComment;
import com.kanban.entity.user.ProfilePicture;
import com.kanban.entity.userboard.UserBoard;
import com.kanban.service.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.kanban.service.UserService.decompressBytes;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
public class UserDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    public String username;

    @Column
    @JsonIgnore
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @OneToMany(mappedBy = "user")
    private List<UserBoard> userBoards = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_projects",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")})
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<ProjectTaskComment> comments;

    @OneToMany(mappedBy = "invitedUser")
    private List<ProjectInvitation> invitations;

    @OneToOne
    private ProfilePicture profilePicture;

    @Column
    private boolean hasProfilePicture = false;

    public void addBoard(UserBoard userBoard) {
        this.userBoards.add(userBoard);
        userBoard.setUser(this);
    }

    public void addProject(Project project) {
        this.projects.add(project);
        project.getUsers().add(this);
    }

    public ProfilePicture getProfilePicture() {
        if (hasProfilePicture) {
            return new ProfilePicture(profilePicture.getName(), profilePicture.getType(), decompressBytes(profilePicture.getPicByte()));
        }
        return null;
    }

    public void deleteProject(Project project){
        this.projects.remove(project);
    }

    public UserBoard getDefaultBoard(){
        return this.getUserBoards().get(0);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

