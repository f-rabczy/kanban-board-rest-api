package com.kanban.controller.project;

import com.kanban.entity.project.ProjectTaskComment;
import com.kanban.model.json.ReturnMessageJSON;
import com.kanban.service.project.ProjectTaskCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userID}/projects/{projectID}/boards/tasks/{taskID}/comments")
public class ProjectTaskCommentController {

    private final ProjectTaskCommentService projectTaskCommentService;


    @GetMapping
    public List<ProjectTaskComment> getComments(@PathVariable Long taskID) {
        try {
            return projectTaskCommentService.getComments(taskID);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addComment(@PathVariable Long taskID, @PathVariable Long userID, @RequestBody String content) {
        try {
            projectTaskCommentService.addComment(taskID, userID, content);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok(new ReturnMessageJSON("Comment successfully added"));
    }

    @PostMapping("{commentID}/reply")
    public ResponseEntity<?> addReply(@PathVariable Long userID, @PathVariable Long commentID,
                                      @RequestBody String content) {
        try {
            projectTaskCommentService.addReply(userID, commentID, content);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok(new ReturnMessageJSON("Reply successfully added"));
    }
}
