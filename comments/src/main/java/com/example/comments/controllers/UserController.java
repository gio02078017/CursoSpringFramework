package com.example.comments.controllers;

import com.example.comments.CommentsApplication;
import com.example.comments.domains.models.User;
import com.example.comments.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path="/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(CommentsApplication.class);

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> addNewUser (@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser (@PathVariable Long id) {

        Optional<User> existingUser = userRepository.findById(id);

        return existingUser.isPresent() ? ResponseEntity.ok(existingUser.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updateUser){
        Optional<User> existingUser = userRepository.findById(id);
        if(existingUser.isPresent()){
            User user = existingUser.get();
            user.setName(updateUser.getName());
            user.setEmail(updateUser.getEmail());
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<User>> deleteUser(@PathVariable Long id){
        Optional<User> existingUser = userRepository.findById(id);
        if(existingUser.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok(userRepository.findAll());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
