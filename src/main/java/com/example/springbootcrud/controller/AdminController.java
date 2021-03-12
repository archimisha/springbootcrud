package com.example.springbootcrud.controller;

import com.example.springbootcrud.model.Role;
import com.example.springbootcrud.model.User;
import com.example.springbootcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/user-list";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "admin/user-create";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "userCheck", required = false) boolean userCheck,
                         @RequestParam(value = "adminCheck", required = false) boolean adminCheck) {
        Set<Role> roles = new HashSet<>();
        if (userCheck) {
            roles.add(new Role(2L, "ROLE_USER"));
        }
        if (adminCheck) {
            roles.add(new Role(1L, "ROLE_ADMIN"));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles().toString());
        return "admin/user-update";
    }

    @PostMapping("/user-update")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "userCheck", required = false) boolean userCheck,
                         @RequestParam(value = "adminCheck", required = false) boolean adminCheck) {
        user.setRoles(null);
        Set<Role> roles = new HashSet<>();
        if (adminCheck) {
            roles.add(new Role(1L, "ROLE_ADMIN"));
        }
        if (userCheck) {
            roles.add(new Role(2L, "ROLE_USER"));
        }
        user.setRoles(roles);
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}

