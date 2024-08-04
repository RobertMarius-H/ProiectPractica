package com.example.databconn.controller;
import com.example.databconn.UserRepo.UserRepository;
import com.example.databconn.model.User;
import com.example.databconn.UserRepo.UserRepository;
import com.example.databconn.model.User;

import com.example.databconn.model.User;
import com.example.databconn.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService = new UserService();
    private final UserRepository userRepository = new UserRepository();

    @GetMapping("/users")
    public Object getUsers(@RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            return userService.getUserById(id);
        } else {
            return userService.getAllUsers();
        }
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam("id") Integer id) {
        userService.deleteUserById(id);
    }

    @PostMapping("/save")
    public String saveUser(@RequestBody User user) {
       userService.saveUser(user);
       return "Saved...";
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return "Updated...";
    }




}