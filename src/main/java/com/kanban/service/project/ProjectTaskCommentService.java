package com.kanban.service.project;

import com.kanban.entity.project.ProjectTask;
import com.kanban.entity.project.ProjectTaskComment;
import com.kanban.entity.project.ProjectTaskCommentReply;
import com.kanban.entity.user.UserDAO;
import com.kanban.repository.project.ProjectTaskCommentRepository;
import com.kanban.service.UserService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectTaskCommentService {
    private ProjectTaskCommentRepository projectTaskCommentRepository;
    private ProjectTaskService projectTaskService;
    private UserService userService;

    public ProjectTaskCommentService(ProjectTaskCommentRepository projectTaskCommentRepository, ProjectTaskService projectTaskService, UserService userService) {
        this.projectTaskCommentRepository = projectTaskCommentRepository;
        this.projectTaskService = projectTaskService;
        this.userService = userService;
    }

    public List<ProjectTaskComment> getComments(Long taskID) {
        ProjectTask task = projectTaskService.getTask(taskID);
        return task.getComments();
    }

    public void addComment(Long taskID, Long userID, String content) {
        ProjectTask task = projectTaskService.getTask(taskID);
        UserDAO user = userService.getUser(userID);
        ProjectTaskComment projectTaskComment = new ProjectTaskComment();

        JSONObject jsonObject = new JSONObject(content);
        String commentContent = jsonObject.getString("content");

        projectTaskComment.setContent(commentContent);
        projectTaskComment.setAuthor(user);

        task.addComment(projectTaskComment);
        projectTaskService.saveTask(task);
    }


    public void addReply( Long userID, Long commentID, String content) {
        Optional<ProjectTaskComment> byId = projectTaskCommentRepository.findById(commentID);
        if(byId.isPresent()){
            ProjectTaskComment projectTaskComment = byId.get();
            UserDAO user = userService.getUser(userID);

            JSONObject jsonObject = new JSONObject(content);
            String commentContent = jsonObject.getString("content");
            ProjectTaskCommentReply projectTaskCommentReply = new ProjectTaskCommentReply();
            projectTaskCommentReply.setAuthor(user);
            projectTaskCommentReply.setContent(commentContent);
            projectTaskComment.addReply(projectTaskCommentReply);
            projectTaskCommentRepository.save(projectTaskComment);
        }

    }
}
