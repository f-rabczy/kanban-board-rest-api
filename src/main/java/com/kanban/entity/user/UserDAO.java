package com.kanban.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanban.entity.project.Project;
import com.kanban.entity.project.ProjectInvitation;
import com.kanban.entity.project.ProjectTaskComment;
import com.kanban.entity.user.ProfilePicture;
import com.kanban.entity.userboard.UserBoard;
import com.kanban.service.UserService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.kanban.service.UserService.decompressBytes;

@Entity
@Table(name = "user")
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
            joinColumns = { @JoinColumn(name = "user_id")},
            inverseJoinColumns = { @JoinColumn(name = "project_id")})
    private List<Project> projects = new ArrayList<>();


    @OneToMany(mappedBy = "author")
    private List<ProjectTaskComment> comments;

    @OneToMany(mappedBy = "invitedUser")
    private List<ProjectInvitation> invitations;

    @OneToOne
    private ProfilePicture profilePicture;

    @Column
    private boolean hasProfilePicture = false;



    public void addBoard(UserBoard userBoard){
        this.userBoards.add(userBoard);
        userBoard.setUser(this);
    }

    public void addProject(Project project){
        this.projects.add(project);
        project.getUsers().add(this);
    }


    public ProfilePicture getProfilePicture() {

        if(hasProfilePicture){
            return new ProfilePicture(profilePicture.getName(),profilePicture.getType(),decompressBytes(profilePicture.getPicByte()));
        }
        return null;
    }



    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserBoard getDefaultBoard(){
        return this.getUserBoards().get(0);
    }

    public List<UserBoard> getUserBoards() {
        return userBoards;
    }

    public void setUserBoards(List<UserBoard> userBoards) {
        this.userBoards = userBoards;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isHasProfilePicture() {
        return hasProfilePicture;
    }

    public void setHasProfilePicture(boolean hasProfilePicture) {
        this.hasProfilePicture = hasProfilePicture;
    }

    public void deleteProject(Project project){
        this.projects.remove(project);
    }



//    public List<Project> getProjects() { ////Creating get method generates exception
//        return projects;
//    }
//
//    public void setProjects(List<Project> projects) {
//        this.projects = projects;
//    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}

