package com.kanban.service;

import com.kanban.entity.userboard.UserBoardColumn;
import com.kanban.entity.user.UserDAO;
import com.kanban.model.dto.TaskDTO;
import com.kanban.model.enums.TaskColor;
import com.kanban.repository.UserTaskRepository;
import com.kanban.entity.userboard.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserTaskService {

    private UserTaskRepository userTaskRepository;
    private UserService userService;

    @Autowired
    public UserTaskService(UserTaskRepository userTaskRepository, UserService userService) {
        this.userTaskRepository = userTaskRepository;
        this.userService = userService;
    }


    public void createNewTask(Long id, TaskDTO task){
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

    public List<TaskColor> getColors(){
        return  Arrays.asList(TaskColor.values());

    }


//    public boolean saveTask(String name, String description, String priority, String status, String username) {
//        DAOUser user = userRepository.findByUsername(username);
//
//        if (user == null) {
//            throw new RuntimeException("This username does not exist");
//        }
//        if (taskRepository.getByUser(user) != null) {
//            return false;
//        }
//
//        Task taskToSave = new Task();
//        taskToSave.setTitle(name);
//        taskToSave.setDescription(description);
//        taskToSave.setStatus(status);
//
//
//        user.addTask(taskToSave);
//        taskRepository.save(taskToSave);
//        System.out.println(user.getTasks());
//        return true;
//    }

//    public List<Task> getTasks(String username){
//        if(userRepository.findByUsername(username)== null){
//            throw new RuntimeException("User does not exist");
//        }
//        DAOUser user = userRepository.findByUsername(username);
//        if(user.getTasks().isEmpty()){
//            throw new RuntimeException("Tasks list is empty");
//        }
//        return taskRepository.getAllByUser(user);
//    }

//    public boolean deleteTask(int id) {
//        if (taskRepository.getById(id) != null) {
//            taskRepository.deleteById(id);
//            return true;
//        }
//        return false;
//    }
//
//    public boolean updateTask(int id, String name, String description, String priority, String status) {
//        if (taskRepository.getById(id) != null) {
//            Task taskToUpdate = taskRepository.getById(id);
//            taskToUpdate.setTitle(name);
//            taskToUpdate.setDescription(description);
//            taskToUpdate.setStatus(status);
//            taskRepository.save(taskToUpdate);
//            return true;
//        }
//        return false;
//    }
//
//    public void changeStatus(int id, String status) throws Exception {
//        if (taskRepository.getById(id) == null) {
//            throw new Exception("Task with given id does not exist");
//        }
//        if (taskRepository.getById(id).getStatus().equals(status)) {
//            throw new Exception("Wrong status given");
//        }
//        Task taskToUpdate = taskRepository.getById(id);
//        taskToUpdate.setStatus(status);
//        taskRepository.save(taskToUpdate);
//    }
}
