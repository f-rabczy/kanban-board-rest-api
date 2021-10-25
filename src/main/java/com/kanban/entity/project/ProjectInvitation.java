package com.kanban.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanban.entity.user.UserDAO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

}
