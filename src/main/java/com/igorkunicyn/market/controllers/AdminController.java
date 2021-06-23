package com.igorkunicyn.market.controllers;

import com.igorkunicyn.market.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String usersList(Model model){
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    public String deleteUser(@RequestParam(defaultValue = "")Long userId,
                             @RequestParam(defaultValue = "")String action,
                             Model model){
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

}
