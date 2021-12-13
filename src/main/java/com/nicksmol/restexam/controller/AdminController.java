package com.nicksmol.restexam.controller;

import com.nicksmol.restexam.model.User;
import com.nicksmol.restexam.service.RoleService;
import com.nicksmol.restexam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAllUsers(Model model, Principal principal, @ModelAttribute("user") User user) {
        model.addAttribute("authUser", userService.findByEmail(principal.getName()));
        model.addAttribute("listRoles", roleService.getAllRoles());
        return "allUsers";
    }
}
