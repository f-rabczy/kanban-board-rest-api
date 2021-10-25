package com.kanban.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanban.entity.user.UserDAO;
import com.kanban.model.enums.TaskColor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.Mergeable;

import javax.persistence.*;
import java.util.*;

import static com.kanban.model.util.DateUtil.dateConverter;

@Entity
@Getter
@Setter
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

    public void setOrder(Integer listOrder) {
        this.listOrder = listOrder;
    }

    public void addComment(ProjectTaskComment comment){
        comments.add(comment);
        comment.setProjectTask(this);
    }

    public ProjectTask(){
        this.date= dateConverter();
    }

    @Override
    public int compareTo(@NotNull ProjectTask o) {
        return Integer.compare(this.listOrder,o.listOrder);
    }

}
