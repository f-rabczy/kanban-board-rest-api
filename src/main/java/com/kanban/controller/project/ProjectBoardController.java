package com.kanban.controller.project;

import com.kanban.entity.project.Project;
import com.kanban.service.project.ProjectBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userID}/projects/{projectID}/boards")
public class ProjectBoardController {

    ProjectBoardService projectBoardService;

    @Autowired
    public ProjectBoardController(ProjectBoardService projectBoardService) {
        this.projectBoardService = projectBoardService;
    }

    @PutMapping()
    public ResponseEntity<?> updateProjectBoard(@PathVariable Long projectID, @RequestBody Project project){
        try{
            projectBoardService.updateProjectBoard(project,projectID);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/name")
    public ResponseEntity<?> updateProjectBoardName(@PathVariable Long projectID, @RequestBody String name){
        try{
            projectBoardService.updateProjectBoardName(projectID,name);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
