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
import java.util.List;

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
    public ResponseEntity<String> registerUser(@RequestBody UserLogin userLogin) {
        try {
            UserLogin user = userLoginService.registerUser(userLogin.getUsername(), userLogin.getPassword(), userLogin.getEmail());
            return ResponseEntity.ok("User registered successfully with ID: " + user.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLogin userLogin) {
        try{
            if (userLoginService.authenticateUser(userLogin.getUsername(), userLogin.getPassword())) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error: " + e.getMessage());
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<User>> filterUsers(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nume,
            @RequestParam(required = false) String prenume,
            @RequestParam(required = false) Integer anNastere,
            @RequestParam(required = false) Integer idOcupatie,
            @RequestParam(required = false) Integer idOrasDomiciliu) {
       // System.out.println("checked CONTROLLER");
        List<User> users = userService.filterUsers(id, nume, prenume, anNastere, idOcupatie, idOrasDomiciliu);
        return ResponseEntity.ok(users);
    }

}

