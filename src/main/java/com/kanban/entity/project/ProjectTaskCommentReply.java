package com.kanban.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanban.entity.user.UserDAO;

import javax.persistence.*;

import static com.kanban.model.util.DateUtil.dateConverter;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDAO getAuthor() {
        return author;
    }

    public void setAuthor(UserDAO author) {
        this.author = author;
    }

    public ProjectTaskComment getComment() {
        return comment;
    }

    public void setComment(ProjectTaskComment comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
