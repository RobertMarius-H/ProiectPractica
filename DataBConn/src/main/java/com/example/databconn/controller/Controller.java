package com.example.databconn.controller;


import com.example.databconn.model.User;
import com.example.databconn.service.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /*

    FOLOSITA CA SA TESTEZ NOUA PAGINA HTML

    @RequestMapping("/users-pagina")
    public String pagina(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "primaPagina";
    }
    */
}