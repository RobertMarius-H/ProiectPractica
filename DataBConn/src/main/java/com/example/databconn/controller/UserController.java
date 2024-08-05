package com.example.databconn.controller;

import com.example.databconn.model.User;
import com.example.databconn.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok("Saved...");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok("Updated...");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam("id") Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("Deleted...");
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(@RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            return ResponseEntity.ok(userService.getUserById(id));
        } else {
            return ResponseEntity.ok(userService.getAllUsers());
        }
    }
}

