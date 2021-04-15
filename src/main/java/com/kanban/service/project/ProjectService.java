package com.kanban.service.project;

import com.kanban.entity.project.*;
import com.kanban.entity.user.UserDAO;
import com.kanban.entity.userboard.UserBoard;
import com.kanban.entity.userboard.UserBoardColumn;
import com.kanban.model.enums.TaskColor;
import com.kanban.repository.project.ProjectRepository;
import com.kanban.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private UserService userService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;

    }

    public Project getProject(Long projectID){
        Project project = projectRepository.findById(projectID).orElse(null);
        if(project!= null){
            sortTaskList(project.getBoard());
        }
        return project;
    }





    public void createProject(Long userID, String projectName){
        Project project = new Project();
        project.setName(projectName);

        UserDAO user = userService.getUser(userID);
        project.setCreator(user);
        project.addBoard(initializeProjectBoard());

        user.addProject(project);
        projectRepository.save(project);
    }

    public void leaveProject(Long userID, Long projectID){
        Project project = getProject(projectID);
        project.getUsers().removeIf(user -> user.getId() == userID);
        UserDAO user = userService.getUser(userID);
        user.deleteProject(project);
        projectRepository.save(project);
        userService.saveUser(user);

    }


    public ProjectBoard initializeProjectBoard() {
        ProjectBoard board = new ProjectBoard();
        board.setName("default board");

        ProjectBoardColumn column1 = new ProjectBoardColumn("To Do");
        ProjectBoardColumn column2 = new ProjectBoardColumn("In Progress");
        ProjectBoardColumn column3 = new ProjectBoardColumn("Done");

        board.addColumn(column1);
        board.addColumn(column2);
        board.addColumn(column3);

        return board;
    }

    public void initializeProjectBoardTest() {
        UserDAO user = userService.getTestUser();
        UserDAO essa = userService.getUser(2L);
        Project project = new Project();
        project.setName("default project 1");
        project.setCreator(user);


        ProjectBoard board = new ProjectBoard();
        board.setName("default board");

        ProjectBoardColumn column1 = new ProjectBoardColumn("To Do");
        ProjectBoardColumn column2 = new ProjectBoardColumn("In Progress");
        ProjectBoardColumn column3 = new ProjectBoardColumn("Done");

        ProjectTask projectTask1 = new ProjectTask();
        projectTask1.setTitle("Organize group meeting");
        projectTask1.setDescription("Everyone has to be invited");
        projectTask1.setStatus("To Do");
        projectTask1.setTaskColor(TaskColor.Red);

        ProjectTaskComment projectTaskComment = new ProjectTaskComment();
        projectTaskComment.setAuthor(user);
        projectTaskComment.setContent("ESSSAAAAAAAAA");



        ProjectTaskCommentReply projectTaskCommentReply = new ProjectTaskCommentReply();
        projectTaskCommentReply.setAuthor(essa);
        projectTaskCommentReply.setContent("MUJ WUJA !!!!");
        projectTaskComment.addReply(projectTaskCommentReply);
        projectTask1.addComment(projectTaskComment);

        ProjectTask projectTask2 = new ProjectTask();
        projectTask2.setTitle("Do the annual report");
        projectTask2.setDescription("A lot of shit to write");
        projectTask2.setStatus("In Progress");
        projectTask2.setTaskColor(TaskColor.Yellow);

        ProjectTask projectTask3 = new ProjectTask();
        projectTask3.setTitle("Rent a room for presentation");
        projectTask3.setDescription("It was ez ");
        projectTask3.setStatus("Done");
        projectTask3.setTaskColor(TaskColor.Purple);

        column1.addTask(projectTask1);
        column2.addTask(projectTask2);
        column3.addTask(projectTask3);

        board.addColumn(column1);
        board.addColumn(column2);
        board.addColumn(column3);

        project.addBoard(board);



        projectRepository.save(project);

        user.addProject(project);
        userService.saveUser(user);




    }

    public void initializeProjectBoardTest2() {
        UserDAO user2 = userService.getTestUser();
        Project project2 = new Project();
        project2.setName("default project 2");
        UserDAO essa = userService.getUser(2L);
        project2.setCreator(essa);


        ProjectBoard board = new ProjectBoard();
        board.setName("default board 2 ");

        ProjectBoardColumn column1 = new ProjectBoardColumn("To Do");
        ProjectBoardColumn column2 = new ProjectBoardColumn("In Progress");
        ProjectBoardColumn column3 = new ProjectBoardColumn("Done");

        ProjectTask projectTask1 = new ProjectTask();
        projectTask1.setTitle("Test1");
        projectTask1.setDescription("Test1");
        projectTask1.setStatus("To Do");
        projectTask1.setTaskColor(TaskColor.Red);

        ProjectTask projectTask2 = new ProjectTask();
        projectTask2.setTitle("Test2");
        projectTask2.setDescription("Test2");
        projectTask2.setStatus("In Progress");
        projectTask2.setTaskColor(TaskColor.Yellow);

        ProjectTask projectTask3 = new ProjectTask();
        projectTask3.setTitle("Test3");
        projectTask3.setDescription("It was ez ");
        projectTask3.setStatus("Done");
        projectTask3.setTaskColor(TaskColor.Purple);

        column1.addTask(projectTask1);
        column2.addTask(projectTask2);
        column3.addTask(projectTask3);

        board.addColumn(column1);
        board.addColumn(column2);
        board.addColumn(column3);

        project2.addBoard(board);
        project2.addUser(user2);


        projectRepository.save(project2);
        userService.saveUser(user2);




    }

    public List<Project> getAllProjects(Long userID) {
        UserDAO user = userService.getUser(userID);
        return projectRepository.findAllByUsersIsContaining(user);
    }

    public void sortTaskList(ProjectBoard projectBoard){
        for (ProjectBoardColumn column : projectBoard.getColumns()) {
            Collections.sort(column.getProjectTaskList());
        }
    }
}
