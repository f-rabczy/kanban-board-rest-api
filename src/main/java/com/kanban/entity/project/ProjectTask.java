package com.kanban.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanban.entity.user.UserDAO;
import com.kanban.model.enums.TaskColor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.Mergeable;

import javax.persistence.*;
import java.util.*;

import static com.kanban.model.util.DateUtil.dateConverter;

@Entity
public class ProjectTask implements Comparable<ProjectTask> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String date;

    @Column
    private String deadline;

    @Column
    private Integer listOrder = 0;

    @Column
    @Enumerated(EnumType.STRING)
    private TaskColor taskColor;

    @ManyToOne
    @JoinColumn(name = "column_id")
    @JsonIgnore
    private ProjectBoardColumn column;

    @OneToOne
    private UserDAO creator;

    @OneToMany(mappedBy = "projectTask", cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE})
    private List<ProjectTaskComment> comments = new ArrayList<>();


    @Transient
    private Set<UserDAO> commentsParticipants = new HashSet<>();

    public void initializeCommentsParticipants(){
        for (ProjectTaskComment comment : comments) {
            this.commentsParticipants.add(comment.getAuthor());
                    if(comment.getReplies().size() > 0){
                        for (ProjectTaskCommentReply reply : comment.getReplies()) {
                            this.commentsParticipants.add(reply.getAuthor());
                        }
                    }
        }
    }



    public void addComment(ProjectTaskComment comment){
        comments.add(comment);
        comment.setProjectTask(this);
    }

    public ProjectTask(){
        this.date= dateConverter();
    }

    public Long getId() {
        return id;
    }

    public Set<UserDAO> getCommentsParticipants() {
        return commentsParticipants;
    }

    public Integer getListOrder() {
        return listOrder;
    }

    @Override
    public int compareTo(@NotNull ProjectTask o) {
        return Integer.compare(this.listOrder,o.listOrder);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Integer getOrder() {
        return listOrder;
    }

    public void setOrder(Integer listOrder) {
        this.listOrder = listOrder;
    }

    public TaskColor getTaskColor() {
        return taskColor;
    }

    public void setTaskColor(TaskColor taskColor) {
        this.taskColor = taskColor;
    }

    public ProjectBoardColumn getColumn() {
        return column;
    }

    public void setColumn(ProjectBoardColumn column) {
        this.column = column;
    }

    public UserDAO getCreator() {
        return creator;
    }

    public void setCreator(UserDAO creator) {
        this.creator = creator;
    }

    public List<ProjectTaskComment> getComments() {
        return comments;
    }

    public void setComments(List<ProjectTaskComment> comments) {
        this.comments = comments;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

}
