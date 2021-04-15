package com.kanban.controller.project;


import com.kanban.model.dto.TaskDTO;
import com.kanban.model.json.ReturnMessageJSON;
import com.kanban.service.project.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userID}/projects/{projectID}/boards/tasks")
public class ProjectTaskController {

    private ProjectTaskService projectTaskService;

    @Autowired
    public ProjectTaskController(ProjectTaskService projectTaskService) {
        this.projectTaskService = projectTaskService;
    }

    @DeleteMapping("/{taskID}")
    public ResponseEntity<?> deleteTask(@PathVariable Long projectID, @PathVariable Long taskID){
        try{
            projectTaskService.deleteTask(taskID);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
        return  ResponseEntity.ok(new ReturnMessageJSON("Task successfully deleted"));
    }

    @PutMapping("/{taskID}")
    public ResponseEntity<?> updateTask(@PathVariable Long projectID, @PathVariable Long taskID, @RequestBody TaskDTO taskDTO){
        try{
            projectTaskService.updateTask(taskID, taskDTO);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
        return  ResponseEntity.ok(new ReturnMessageJSON("Task successfully updated"));
    }

    @PostMapping
    public ResponseEntity<?> createTask(@PathVariable Long projectID,@PathVariable Long userID,@RequestBody TaskDTO taskDTO){
        try{
            projectTaskService.createTask(taskDTO, projectID, userID);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
        return  ResponseEntity.ok(new ReturnMessageJSON("Task successfully created"));
    }

}
