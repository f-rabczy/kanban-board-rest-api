package com.kanban.controller;


import com.kanban.model.dto.TaskDTO;
import com.kanban.model.enums.TaskColor;
import com.kanban.model.json.ReturnMessageJSON;
import com.kanban.service.UserTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "users/{userID}/tasks")
@CrossOrigin
@RequiredArgsConstructor
public class UserTaskController {

    private final UserTaskService userTaskService;

    @PostMapping
    public ResponseEntity<?> createNewTask(@PathVariable Long userID, @RequestBody TaskDTO task) {
        try {
            userTaskService.createNewTask(userID, task);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return ResponseEntity.ok(new ReturnMessageJSON("Task successfully created"));
    }

    @DeleteMapping("/{taskID}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskID) {
        try {
            userTaskService.deleteTask(taskID);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return ResponseEntity.ok(new ReturnMessageJSON("Task successfully deleted"));
    }

    @PutMapping("/{taskID}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskID, @RequestBody TaskDTO taskToUpdate) {
        try {
            userTaskService.updateTask(taskID, taskToUpdate);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return ResponseEntity.ok(new ReturnMessageJSON("Task successfully updated"));
    }

    @GetMapping("/colors")
    public ResponseEntity<List<TaskColor>> getColors() {
        return new ResponseEntity<>(userTaskService.getColors(), HttpStatus.OK);
    }

}