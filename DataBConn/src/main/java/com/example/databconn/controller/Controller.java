package com.example.databconn.controller;


import com.example.databconn.model.User;
import com.example.databconn.service.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    private final UserService userService = new UserService();

    @RequestMapping("/home")
    public String test(){
        return "index";
    }

    @RequestMapping("/users-view")
    public String getUsersView(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "index";
    }
}
