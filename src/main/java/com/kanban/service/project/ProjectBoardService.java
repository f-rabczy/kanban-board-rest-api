package com.kanban.service.project;

import com.kanban.entity.project.Project;
import com.kanban.entity.project.ProjectBoard;
import com.kanban.entity.project.ProjectBoardColumn;
import com.kanban.repository.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectBoardService {

    private final ProjectRepository projectRepository;


    @Transactional
    public void updateProjectBoard(Project project, Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            Project projectToUpdate = projectOptional.get();
            ProjectBoard boardToUpdate = projectToUpdate.getBoard();
            boardToUpdate.getColumns().clear();
            for (ProjectBoardColumn column : project.getBoard().getColumns()) {
                if (!column.getProjectTaskList().isEmpty()) {
                    boardToUpdate.addColumnWithTasks(column);
                } else {
                    boardToUpdate.addColumn(column);
                }
            }
            projectRepository.save(projectToUpdate);
        }
    }

    public void updateProjectBoardName(Long id, String name) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            ProjectBoard projectBoardToUpdate = project.getBoard();
            JSONObject jsonObject = new JSONObject(name);
            String newName = jsonObject.getString("name");
            if (projectBoardToUpdate.getName().equals(newName)) {
                throw new RuntimeException("New name is the same as the previous one");
            }
            projectBoardToUpdate.setName(newName);
            projectRepository.save(project);
        }
    }
}
