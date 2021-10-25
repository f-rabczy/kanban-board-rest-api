package com.kanban.controller.project;

import com.kanban.entity.project.Project;
import com.kanban.model.json.ReturnMessageJSON;
import com.kanban.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userID}/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createProject(@PathVariable Long userID, String projectName) {
        try {
            projectService.createProject(userID, projectName);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok(new ReturnMessageJSON("Project successfully created"));
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(@PathVariable Long userID) {
        return new ResponseEntity<>(projectService.getAllProjects(userID), HttpStatus.OK);
    }

    @GetMapping("/{projectID}")
    public Project getProject(@PathVariable Long userID, @PathVariable Long projectID) {
        return projectService.getProject(projectID);

    }

    @PutMapping("/{projectID}")
    public ResponseEntity<?> leaveProject(@PathVariable Long userID, @PathVariable Long projectID) {
        try {
            projectService.leaveProject(userID, projectID);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return ResponseEntity.ok(new ReturnMessageJSON("You have left a project successfully"));

    }
}
