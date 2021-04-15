package com.kanban.controller;

import com.kanban.entity.user.ProfilePicture;
import com.kanban.entity.user.UserDAO;
import com.kanban.model.dto.UserDTO;
import com.kanban.model.json.ReturnMessageJSON;
import com.kanban.service.JwtUserDetailsService;
import com.kanban.service.project.ProjectService;
import com.kanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path = "/users")
public class UserController {

    UserService userService;
    JwtUserDetailsService service;
    ProjectService projectService;

    @Autowired
    public UserController(UserService userService, JwtUserDetailsService service, ProjectService service1) {
        this.service = service;
        this.userService = userService;
        this.projectService = service1;
        UserDTO user = new UserDTO();
        user.setUsername("12345");
        user.setPassword("12345");
        user.setEmail("eliot.lana@yahoo.com");
        user.setFirstName("Eliot");
        user.setLastName("Anderson");

        UserDTO user2 = new UserDTO();
        user2.setUsername("123456");
        user2.setPassword("123456");
        user2.setEmail("bgcnarejonie@gmail.com");
        user2.setFirstName("Peter");
        user2.setLastName("Witchuck");

        service.saveDTO(user);
        service.saveDTO(user2);
        service1.initializeProjectBoardTest();
        service1.initializeProjectBoardTest2();
    }

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
    public ProfilePicture downloadImage(@PathVariable Long id){
        try{

            return userService.downloadImage(id);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

}



