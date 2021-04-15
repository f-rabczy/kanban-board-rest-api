package com.kanban.controller;

import com.kanban.entity.userboard.UserBoard;

import com.kanban.model.json.ReturnMessageJSON;
import com.kanban.service.UserBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping(path = "users/{userID}/boards")
public class UserBoardController {

    UserBoardService userBoardService;

    @Autowired
    public UserBoardController(UserBoardService userBoardService) {
        this.userBoardService = userBoardService;
    }

    @GetMapping
    public UserBoard getUserBoard(@PathVariable Long userID) {
        try {
            return userBoardService.getUserBoard(userID);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @PutMapping("/{boardID}")
    public ResponseEntity<?> updateUserBoard(@RequestBody UserBoard userBoard, @PathVariable Long boardID) {
        try {
            userBoardService.updateUserBoard(userBoard, boardID);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return ResponseEntity.ok(new ReturnMessageJSON("Board successfully updated"));
    }

    @PutMapping("/{boardID}/name")
    public ResponseEntity<?> updateUserBoardName(@PathVariable Long boardID, @RequestBody String name) {
        try {
            userBoardService.updateUserBoardName(name, boardID);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return ResponseEntity.ok(new ReturnMessageJSON("Board's name successfully updated"));
    }

}
