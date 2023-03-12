package com.example.pmbakanov.controllers;

import com.example.pmbakanov.models.User;
import com.example.pmbakanov.services.UserService;
import lombok.RequiredArgsConstructor;
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
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public String login(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Principal principal,
                          Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("loginalert", "Код активации отправлен на указанную почту");
        return "profile";
    }

    @GetMapping("/reset")
    public String passwordReset(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "password-reset";
    }

    @PostMapping("/reset")
    public String reset(User user, Model model) {
        if (!userService.resetPassword(user)) {
            model.addAttribute("errorMessage", "Пользователь с эл.почтой: " + user.getEmail() + " не найден");
            return "password-reset";
        }
        model.addAttribute("loginalert", "Ссылка для cброса пароля отправлена на указанную почту");
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Пользователь с эл.почтой: " + user.getEmail() + " уже существует");
            return "registration";
        }
        model.addAttribute("loginalert", "Ссылка для активации отправлена на указанную почту");
        return "login";
    }

    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model, Principal principal) {
        model.addAttribute("user", user);
        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
        model.addAttribute("records", user.getRecords());
        model.addAttribute("requests", user.getRequests());
        return "user-info";
    }

    @GetMapping("/activate/{code}")
    public String activate(Principal principal, Model model, @PathVariable String code) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("loginmessage", "Пользователь успешно активирован");
        } else {
            model.addAttribute("loginmessage", "Код активации не найден");
        }
        return "login";
    }

    @GetMapping("/reset/{code}")
    public String checkCodeForReset(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("user", userService.findUserByCode(code));
            return "newpassword";
        } else {
            model.addAttribute("loginmessage", "Код сброса не найден");
        }
        return "login";
    }

    @PostMapping("/newpassword")
    public String setNewPassword(@RequestParam("userId") User user, @RequestParam Map<String, String> form, Model model) {
        userService.changeUserPassword(user,form);
        //model.addAttribute("loginalert", "Пароль успешно установлен");
        return "redirect:/login";
    }
}
