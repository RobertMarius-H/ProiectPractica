package com.example.databconn.controller;

import com.example.databconn.model.User;
import com.example.databconn.service.UserService;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController

@RequestMapping("/users")
public class UserController {
    private final UserService userService = new UserService();

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

}

