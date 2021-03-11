package com.example.springbootcrud.controller;

import com.example.springbootcrud.model.User;
import com.example.springbootcrud.service.RoleService;
import com.example.springbootcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {
    private final UserService userService;

    private final RoleService roleService;

    public LoginController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String newUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/createUser")
    public String registration(@ModelAttribute("user") User user) {
//        if (user.getFirstName().equals("admin")) {
//            user.getRoles().add(roleService.getAdminRole());
//        } else {
//            user.getRoles().add(roleService.getDefaultRole());
//        }
        user.getRoles().add(roleService.getDefaultRole());
        userService.saveUser(user);

        return "redirect:/login";
    }
}
