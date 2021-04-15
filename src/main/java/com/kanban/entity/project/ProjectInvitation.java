package com.kanban.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanban.entity.user.UserDAO;

import javax.persistence.*;

@Entity
public class ProjectInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id ;

    @Column
    private String inviterName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserDAO invitedUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInviterName() {
        return inviterName;
    }

    public void setInviterName(String inviterName) {
        this.inviterName = inviterName;
    }

    public UserDAO getInvitedUser() {
        return invitedUser;
    }

    public void setInvitedUser(UserDAO invitedUser) {
        this.invitedUser = invitedUser;
    }
}
