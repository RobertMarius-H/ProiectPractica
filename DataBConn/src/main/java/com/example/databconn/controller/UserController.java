package com.example.databconn.controller;

import com.example.databconn.model.User;
import com.example.databconn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController


public class UserController {

    private UserService userService = new UserService();

    @GetMapping("/users")
    public Object getUsers(@RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            return userService.getUserById(id);
        } else {
            return userService.getAllUsers();
        }
    }



}

