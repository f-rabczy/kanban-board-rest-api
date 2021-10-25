package com.kanban.service;


import com.kanban.entity.userboard.UserBoard;
import com.kanban.entity.userboard.UserBoardColumn;

import com.kanban.repository.UserBoardColumnRepository;
import com.kanban.repository.UserBoardRepository;
import com.kanban.repository.UserTaskRepository;
import com.kanban.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import org.json.JSONObject;

@Service
@RequiredArgsConstructor
public class UserBoardService {

    private final UserBoardRepository userBoardRepository;
    private final UserService userService;

    public UserBoard getUserBoard(Long id) {
        UserBoard userBoardToSend = userService.getUser(id).getDefaultBoard();
        sortTaskList(userBoardToSend);
        return userBoardToSend;
    }

    public void updateUserBoard(UserBoard userBoard, Long id) {
        Optional<UserBoard> boardOptional = userBoardRepository.findById(id);
        if (boardOptional.isPresent()) {
            UserBoard userBoardToUpdate = boardOptional.get();
            userBoardToUpdate.getColumns().clear();
            for (UserBoardColumn column : userBoard.getColumns()) {
                if (!column.getUserTaskList().isEmpty()) {
                    userBoardToUpdate.addColumnWithTasks(column);
                } else {
                    userBoardToUpdate.addColumn(column);
                }
            }
            userBoardRepository.save(userBoardToUpdate);
        }
    }

    public void updateUserBoardName(String name, Long id) {
        Optional<UserBoard> boardOptional = userBoardRepository.findById(id);

        if (boardOptional.isPresent()) {
            UserBoard userBoardToUpdate = boardOptional.get();
            JSONObject jsonObject = new JSONObject(name);
            String newName = jsonObject.getString("name");
            if (userBoardToUpdate.getName().equals(newName)) {
                throw new RuntimeException("New name is the same as the previous one");
            }
            userBoardToUpdate.setName(newName);
            userBoardRepository.save(userBoardToUpdate);
        }
    }

    public void sortTaskList(UserBoard userBoard) {
        for (UserBoardColumn column : userBoard.getColumns()) {
            Collections.sort(column.getUserTaskList());
        }
    }

}
