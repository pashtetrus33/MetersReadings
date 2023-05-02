package com.example.pmbakanov.controllers;

import com.example.pmbakanov.models.User;
import com.example.pmbakanov.models.enums.Role;
import com.example.pmbakanov.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        List<User> userList = userService.list();
        Collections.sort(userList);
        model.addAttribute("users", userList);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "admin";
    }


    @GetMapping("/admin/user/edit/{user}")
    public String userEdit(@PathVariable("user") User user, Model model, Principal principal) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    @PostMapping("/admin/user/edit")
    public String userEdit(@RequestParam("userId") User user, @RequestParam Map<String, String> form) {
        userService.changeUserRoles(user, form);
        return "redirect:/admin";
    }

    @PostMapping("/admin/user/password_change")
    public String userPasswordChange(@RequestParam("userId") User user, @RequestParam Map<String, String> form) {
        userService.changeUserPassword(user, form);
        return "redirect:/admin";
    }

    @PostMapping("/admin/user/delete")
    public String userDelete(@RequestParam("userId") User user) {
        userService.deleteUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/sendmail")
    public String sendMail(@RequestParam(value = "email") String email, @RequestParam("message") String message, User user, Model model) {
        userService.sendEmail(email, message);
        model.addAttribute("loginalert", "Письмо успешно отправлено");
        List<User> userList = userService.list();
        Collections.sort(userList);
        model.addAttribute("users", userList);
        return "admin";
    }

    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model, Principal principal) {
        model.addAttribute("user", user);
        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
        model.addAttribute("records", user.getRecords());
        model.addAttribute("requests", user.getRequests());
        model.addAttribute("electricityRecords", user.getElectricityRecords());
        return "user-info";
    }
}
