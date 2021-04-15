package com.kanban.service.project;

import com.kanban.entity.project.Project;
import com.kanban.entity.project.ProjectBoard;
import com.kanban.entity.project.ProjectBoardColumn;
import com.kanban.entity.project.ProjectTask;
import com.kanban.entity.user.UserDAO;
import com.kanban.model.dto.TaskDTO;
import com.kanban.model.enums.TaskColor;
import com.kanban.repository.project.ProjectTaskRepository;
import com.kanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectTaskService {
    private ProjectService projectService;
    private UserService userService;
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    public ProjectTaskService(ProjectService projectService, UserService userService, ProjectTaskRepository projectTaskRepository) {
        this.projectService = projectService;
        this.userService = userService;
        this.projectTaskRepository = projectTaskRepository;
    }

    public void deleteTask( Long taskID) {
        projectTaskRepository.deleteById(taskID);
    }

    public void updateTask(Long taskID, TaskDTO taskDTO) {
        Optional<ProjectTask> taskOptional = projectTaskRepository.findById(taskID);
        if(taskOptional.isPresent()){
            ProjectTask projectTask = taskOptional.get();
            if (projectTask.getTitle().equals(taskDTO.getTitle()) &&
                    projectTask.getDescription().equals(taskDTO.getDescription()) &&
                    projectTask.getTaskColor().getName().equals(taskDTO.getTaskColor())) {
                throw new RuntimeException("Given task has not been edited");
            }
            projectTask.setDescription(taskDTO.getDescription());
            projectTask.setTitle(taskDTO.getTitle());
            projectTask.setTaskColor(TaskColor.valueOf(taskDTO.getTaskColor()));
            projectTaskRepository.save(projectTask);
        }
    }

    public void createTask(TaskDTO taskDTO, Long projectID, Long userID){
        Project project = projectService.getProject(projectID);
        UserDAO user = userService.getUser(userID);

        ProjectTask projectTask = new ProjectTask();
        projectTask.setCreator(user);
        projectTask.setTitle(taskDTO.getTitle());
        projectTask.setDescription(taskDTO.getDescription());
        projectTask.setTaskColor(TaskColor.valueOf(taskDTO.getTaskColor()));
        projectTask.setStatus(taskDTO.getStatus());
        if(taskDTO.getDeadline()!=null){
            projectTask.setDeadline(taskDTO.getDeadline());
        }
        ProjectBoard board = project.getBoard();
        for (ProjectBoardColumn column : board.getColumns()) {
            if(column.getName().equals(taskDTO.getStatus())){
                column.addTask(projectTask);
            }
        }

        projectTaskRepository.save(projectTask);

    }

    public ProjectTask getTask(Long id){
        return projectTaskRepository.findById(id).orElse(null);
    }

    public void saveTask(ProjectTask projectTask){ projectTaskRepository.save(projectTask);}
}
