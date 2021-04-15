package com.kanban.service;

import java.util.ArrayList;

import com.kanban.entity.userboard.UserBoard;
import com.kanban.entity.userboard.UserBoardColumn;
import com.kanban.entity.userboard.UserTask;
import com.kanban.model.enums.TaskColor;
import com.kanban.repository.UserBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kanban.repository.UserRepository;
import com.kanban.entity.user.UserDAO;
import com.kanban.model.dto.UserDTO;

import javax.annotation.PostConstruct;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired

    private  UserRepository userRepository;
    @Autowired

    private  PasswordEncoder encoder;
    @Autowired

    private  UserBoardRepository userBoardRepository;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDAO user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public UserDAO saveDTO(UserDTO user) {
        UserDAO newUser = new UserDAO();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.addBoard(initializeUserBoard(newUser));

        return userRepository.save(newUser);

    }


    public UserBoard initializeUserBoard(UserDAO user) {
        UserBoard userBoard = new UserBoard();
        userBoard.setName("default board");

        UserBoardColumn column1 = new UserBoardColumn("To Do");
        UserBoardColumn column2 = new UserBoardColumn("In Progress");
        UserBoardColumn column3 = new UserBoardColumn("Done");

        UserTask userTask1 = new UserTask();
        userTask1.setTitle("Go to sleep");
        userTask1.setDescription("At least before 11PM");
        userTask1.setStatus("To Do");
        userTask1.setTaskColor(TaskColor.White);

        UserTask userTask4 = new UserTask();
        userTask4.setTitle("Go to school");
        userTask4.setDescription("At least before AM");
        userTask4.setStatus("To Do");
        userTask4.setTaskColor(TaskColor.Blue);

        UserTask userTask5 = new UserTask();
        userTask5.setTitle("Go to Stachursky Concert");
        userTask5.setDescription("At least once before own death");
        userTask5.setStatus("To Do");
        userTask5.setTaskColor(TaskColor.Green);

        UserTask userTask2 = new UserTask();
        userTask2.setTitle("Do the homework");
        userTask2.setDescription("A lot of math exercises");
        userTask2.setStatus("In Progress");
        userTask2.setTaskColor(TaskColor.Yellow);

        UserTask userTask3 = new UserTask();
        userTask3.setTitle("Eat pack of crisps");
        userTask3.setDescription("It was delicious ");
        userTask3.setStatus("Done");
        userTask3.setTaskColor(TaskColor.Purple);

        column1.addTask(userTask1);
        column1.addTask(userTask4);
        column1.addTask(userTask5);
        column1.addTask(userTask2);
        column1.addTask(userTask3);

        userBoard.addColumn(column1);
        userBoard.addColumn(column2);
        userBoard.addColumn(column3);

        user.addBoard(userBoard);


        userBoardRepository.save(userBoard);
        return userBoard;
    }
}