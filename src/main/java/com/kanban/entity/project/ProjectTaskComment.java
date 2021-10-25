package com.kanban.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanban.entity.user.UserDAO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static com.kanban.model.util.DateUtil.dateConverter;

@Entity
@Getter
@Setter
public class ProjectTaskComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserDAO author;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonIgnore
    private ProjectTask projectTask;

    @OneToMany(mappedBy = "comment", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<ProjectTaskCommentReply> replies = new ArrayList<>();

    @Column(nullable = false)
    private String date;

    public ProjectTaskComment() {
        this.date = dateConverter();
    }

    public void addReply(ProjectTaskCommentReply reply){
        this.replies.add(reply);
        reply.setComment(this);
    }

}
