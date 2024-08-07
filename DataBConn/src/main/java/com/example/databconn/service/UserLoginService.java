package com.example.databconn.service;

import com.example.databconn.UserRepo.UserLoginRepository;
import com.example.databconn.model.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class UserLoginService {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserLogin registerUser(String username, String password) throws SQLException {
        if (userLoginRepository.findByUsername(username) != null) {
            throw new RuntimeException("User already exists");
        }

        UserLogin newUser = new UserLogin();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        userLoginRepository.save(newUser);
        return newUser;
    }

    public boolean authenticateUser(String username, String password) throws SQLException {
        UserLogin user = userLoginRepository.findByUsername(username);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
}