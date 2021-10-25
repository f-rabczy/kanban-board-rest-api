package com.kanban.controller;

import com.kanban.entity.user.ProfilePicture;
import com.kanban.entity.user.UserDAO;
import com.kanban.model.dto.UserDTO;
import com.kanban.model.json.ReturnMessageJSON;
import com.kanban.service.JwtUserDetailsService;
import com.kanban.service.project.ProjectService;
import com.kanban.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDAO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            userService.updateUser(id, userDTO);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return ResponseEntity.ok(new ReturnMessageJSON("User successfully updated"));
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<?> uploadImage(@RequestParam("imageFile") MultipartFile file, @PathVariable Long id) {
        try {
            userService.uploadImage(file, id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return ResponseEntity.ok(new ReturnMessageJSON("Image successfully saved"));
    }

    @GetMapping("/{id}/images")
    public ProfilePicture downloadImage(@PathVariable Long id) {
        try {
            return userService.downloadImage(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}



