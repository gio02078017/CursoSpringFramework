package com.example.comments.controllers;

import com.example.comments.CommentsApplication;
import com.example.comments.domains.models.User;
import com.example.comments.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@RestController
@RequestMapping(path="/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(CommentsApplication.class);

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public User addNewUser (@RequestBody User user) {

        System.out.println(user);

        User userResponse =  userRepository.save(user);
        userRepository.findAll().forEach(usera -> {
            log.info(usera.toString());

        });
        return userResponse;
    }

    @GetMapping("/{id}")
    public User getUser (@PathVariable Long id) {

        var getUser = userRepository.findById(id).orElse(null);
        return getUser;
    }

    @GetMapping
    public List<User> getAllUser(){
        return userRepository.findAll();
    }






}
