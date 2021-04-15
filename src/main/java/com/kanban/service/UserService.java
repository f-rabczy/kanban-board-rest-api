package com.kanban.service;


import com.kanban.entity.user.ProfilePicture;
import com.kanban.entity.user.UserDAO;
import com.kanban.model.dto.UserDTO;
import com.kanban.repository.ProfilePictureRepository;
import com.kanban.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class UserService {

    UserRepository userRepository;
    ProfilePictureRepository profilePictureRepository;

    @Autowired
    public UserService(UserRepository userRepository, ProfilePictureRepository profilePictureRepository) {
        this.userRepository = userRepository;
        this.profilePictureRepository = profilePictureRepository;
    }

    public void saveUser(UserDAO user){
        userRepository.save(user);
    }

    public void deleteUser(Long userID){
        userRepository.deleteById(userID);
    }

    public  UserDAO getTestUser(){
       return  getUser(1L);
    }

    public boolean checkIfUserExist(String username){
        return userRepository.findByUsername(username) != null;
    }

    public long getIdByUsername(String username){
       return userRepository.findByUsername(username).getId();
    }

    public UserDAO getUser(Long id){

        Optional<UserDAO> byId = userRepository.findById(id);
        return byId.orElse(null);
    }

    public void updateUser(Long id, UserDTO user){
        UserDAO userToUpdate = getUser(id);


        if(userToUpdate!=null){
            boolean hasChanged = false;
            if(!userToUpdate.getFirstName().equals(user.getFirstName()) && user.getFirstName()!= null ){
                userToUpdate.setFirstName(user.getFirstName());
                hasChanged=true;
            }

            if(!userToUpdate.getLastName().equals(user.getLastName()) && user.getLastName()!= null){
                userToUpdate.setLastName(user.getLastName());
                hasChanged=true;
            }

            if(!userToUpdate.getEmail().equals(user.getEmail()) && user.getEmail()!=null){
                userToUpdate.setEmail(user.getEmail());
                hasChanged=true;
            }

            if(hasChanged){
                userRepository.save(userToUpdate);
            }
        }
    }

    public void uploadImage(MultipartFile file, Long userID)  {
        UserDAO user = getUser(userID);
        String fileName = "profilePicture" + userID;
        try {
            ProfilePicture profilePicture = new ProfilePicture(fileName,file.getContentType(),compressBytes(file.getBytes()));
            user.setProfilePicture(profilePicture);
            user.setHasProfilePicture(true);
            profilePictureRepository.save(profilePicture);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ProfilePicture downloadImage(Long userID){
        UserDAO user = getUser(userID);
        Optional<ProfilePicture> byName = profilePictureRepository.findByName(user.getProfilePicture().getName());
        if(byName.isPresent()){
             return new ProfilePicture(byName.get().getName(),byName.get().getType(),decompressBytes(byName.get().getPicByte()));
        }
        else{
            throw new RuntimeException("Something went wrong");
        }
    }

    public static   byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            throw new RuntimeException(e.getMessage());
        }
        return outputStream.toByteArray();
    }



}
