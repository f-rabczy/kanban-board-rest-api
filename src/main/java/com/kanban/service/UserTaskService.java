package com.kanban.service;

import com.kanban.entity.userboard.UserBoardColumn;
import com.kanban.entity.user.UserDAO;
import com.kanban.model.dto.TaskDTO;
import com.kanban.model.enums.TaskColor;
import com.kanban.repository.UserTaskRepository;
import com.kanban.entity.userboard.UserTask;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;
    private final UserService userService;

    public void createNewTask(Long id, TaskDTO task) {
        UserDAO user = userService.getUser(id);
        String status = task.getStatus();
        UserTask userTaskToSave = new UserTask();
        userTaskToSave.setTitle(task.getTitle());
        userTaskToSave.setDescription(task.getDescription());
        userTaskToSave.setStatus(status);
        userTaskToSave.setTaskColor(TaskColor.valueOf(task.getTaskColor()));

        for (UserBoardColumn column : user.getDefaultBoard().getColumns()) {
            if (column.getName().equals(status)) {
                column.addTask(userTaskToSave);
                userTaskRepository.save(userTaskToSave);
            }
        }
    }

    public void deleteTask(Long taskID) {
        userTaskRepository.deleteById(taskID);
    }

    public void updateTask(Long taskID, TaskDTO taskToUpdate) {
        Optional<UserTask> taskOptional = userTaskRepository.findById(taskID);
        if (taskOptional.isPresent()) {
            UserTask userTask = taskOptional.get();
            if (userTask.getTitle().equals(taskToUpdate.getTitle()) &&
                    userTask.getDescription().equals(taskToUpdate.getDescription()) &&
                    userTask.getTaskColor().getName().equals(taskToUpdate.getTaskColor())) {
                throw new RuntimeException("Given task has not been edited");
            }
            userTask.setDescription(taskToUpdate.getDescription());
            userTask.setTitle(taskToUpdate.getTitle());
            userTask.setTaskColor(TaskColor.valueOf(taskToUpdate.getTaskColor()));
            userTaskRepository.save(userTask);
        }

    }

    public List<TaskColor> getColors() {
        return Arrays.asList(TaskColor.values());

    }
}
