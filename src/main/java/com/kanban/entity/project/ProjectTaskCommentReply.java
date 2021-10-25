package com.kanban.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanban.entity.user.UserDAO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static com.kanban.model.util.DateUtil.dateConverter;

@Entity
@Getter
@Setter
public class ProjectTaskCommentReply {

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
    @JoinColumn(name = "comment_id")
    @JsonIgnore
    private ProjectTaskComment comment;

    @Column(nullable = false)
    private String date;

    public ProjectTaskCommentReply() {
        this.date = dateConverter();
    }

}
