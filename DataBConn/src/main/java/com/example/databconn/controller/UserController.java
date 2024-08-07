package com.example.databconn.controller;

import com.example.databconn.model.User;
import com.example.databconn.model.UserLogin;
import com.example.databconn.service.UserLoginService;
import com.example.databconn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

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

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String username, @RequestParam String password) {
        try {
            UserLogin user = userLoginService.registerUser(username, password);
            return ResponseEntity.ok("User registered successfully with ID: " + user.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        try {
            if (userLoginService.authenticateUser(username, password)) {
                return "Login successful";
            } else {
                return "Invalid username or password";
            }
        } catch (SQLException e) {
            return "Database error: " + e.getMessage();
        }
    }


}

