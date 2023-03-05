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
import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("users", userService.list());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "admin";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    @GetMapping("/allusersrecords")
    public String alluserrecords(Model model, Principal principal) {
        model.addAttribute("users", userService.list());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "alluserrecords";
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    @GetMapping("/allusersrequests")
    public String allusersrequests(Model model, Principal principal) {
        model.addAttribute("users", userService.list());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "alluserrequests";
    }

    @GetMapping("/registration")
    public String registration(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Пользователь с именем: " + user.getLogin() + " уже существует");
            return "registration";
        }
        return "redirect:/profile";
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
}
