package com.example.controller;

import com.example.dao.User;
import com.example.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Controller
public class RootContoller {
    @Autowired
    private UserRepository userRepository;
    @RequestMapping("/demo")
    public String toLoginPage(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("users",
                users);
        return "userPage";
    }
}
