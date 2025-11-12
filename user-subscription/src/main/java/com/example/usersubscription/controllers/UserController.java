package com.example.usersubscription.controllers;

import com.example.usersubscription.entities.User;
import com.example.usersubscription.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/api/register")
    public User register(@RequestBody User user)
    {
        return userService.register(user);
    }
}
