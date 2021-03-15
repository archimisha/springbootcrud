package com.example.springbootcrud.controller;

import com.example.springbootcrud.model.Role;
import com.example.springbootcrud.model.User;
import com.example.springbootcrud.service.RoleService;
import com.example.springbootcrud.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String findAll(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("listRoles", roleService.getAllRoles());
        model.addAttribute("listUser", userService.findAll());
        model.addAttribute("user", user);
        return "admin/user-list-btsrp";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "admin/user-create";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute User user,
                         @RequestParam(value = "roless") String[] role) throws NotFoundException {
        Set<Role> rolesSet = new HashSet<>();
        for (String roles : role) {
            rolesSet.add(roleService.getByName(roles));
        }
        user.setRoles(rolesSet);
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

    @PostMapping("/{id}")
    public String update(@ModelAttribute User user,
                         @RequestParam(value = "roless") String[] role) throws NotFoundException {
        user.setRoles(null);
        Set<Role> rolesSet = new HashSet<>();
        for (String roles : role) {
            rolesSet.add(roleService.getByName(roles));
        }
        user.setRoles(rolesSet);
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/del")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}

