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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    public static int TIME_SHIFT = 4; // смещение в часах для корректного отображения локального времени
    private final UserService userService;

    /**
     * Метод возвращающий представление страницы авторизации
     *
     * @param principal текущий залогинившийся пользователь
     * @param model     интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @return представление страницы авторизации
     */
    @GetMapping("/")
    public String login(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("datatime", LocalDateTime.now().minusHours(TIME_SHIFT).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        return "login";
    }

    /**
     * Метод возвращающий представление страницы профиля пользователя
     *
     * @param principal текущий залогинившийся пользователь
     * @param model     интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @return представление страницы профиля пользователя
     */
    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("loginalert", "Код активации отправлен на указанную почту");
        return "profile";
    }

    /**
     * Метод сброса пароля пользователя
     * @param principal текущий залогинившийся пользователь
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @return представление страницы сброса пароля
     */
    @GetMapping("/reset")
    public String passwordReset(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "password-reset";
    }

    /**
     * Метод возварщающий представление страницы сброса пароля если пользователь с эл. почтой найден
     * @param user сущность пользователя
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @return представление старницы сброса пользователя либо старицы авторизации
     */
    @PostMapping("/reset")
    public String reset(User user, Model model) {
        if (!userService.resetPassword(user)) {
            model.addAttribute("errorMessage", "Пользователь с эл.почтой: " + user.getEmail() + " не найден");
            return "password-reset";
        }
        model.addAttribute("loginalert", "Ссылка для cброса пароля отправлена на указанную почту");
        model.addAttribute("datatime", LocalDateTime.now().minusHours(TIME_SHIFT).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        return "login";
    }

    /**
     * Метод возвращающий представление страницы регистрации
     * @param principal текущий залогинившийся пользователь
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @return представление страницы регистрации
     */
    @GetMapping("/registration")
    public String registration(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "registration";
    }

    /**
     * Метод регистрации пользователя
     * @param user сущность пользователя
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @return представление страницы авторизации
     */
    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Пользователь с эл.почтой: " + user.getEmail() + " уже существует");
            return "registration";
        }
        model.addAttribute("loginalert", "Ссылка для активации отправлена на указанную почту");
        model.addAttribute("datatime", LocalDateTime.now().minusHours(TIME_SHIFT).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        return "login";
    }

    /**
     * Метод активации пользователя
     * @param principal текущий залогинившийся пользователь
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @param code код активации
     * @return представление страницы авторизации
     */
    @GetMapping("/activate/{code}")
    public String activate(Principal principal, Model model, @PathVariable String code) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        User user = userService.activateUser(code);

        if (user != null) {
            model.addAttribute("loginmessage", "Пользователь успешно активирован");
        } else {
            model.addAttribute("loginmessage", "Код активации не найден");
        }
        return "login";
    }

    /**
     * Метод проверки кода активации для сброса пароля пользователя
     * @param principal текущий залогинившийся пользователь
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @param code код активации
     * @return представление страницы авторизации
     */
    @GetMapping("/reset/{code}")
    public String checkCodeForReset(Principal principal, Model model, @PathVariable String code) {
        User user = userService.activateUser(code);
        if (user != null) {
            model.addAttribute("user", user);
            return "newpassword";
        } else {
            model.addAttribute("user", userService.getUserByPrincipal(principal));
            model.addAttribute("loginmessage", "Код сброса не найден");
        }
        return "login";
    }

    /**
     * Метод установки нового пароля пользователя
     * @param principal текущий залогинившийся пользователь
     * @param user сущность пользователя
     * @param form форма из представления
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @return представление страницы авторизации
     */
    @PostMapping("/newpassword")
    public String setNewPassword(Principal principal, @RequestParam("userId") User user, @RequestParam Map<String, String> form, Model model) {
        userService.changeUserPassword(user, form);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("loginmessage", "Пароль успешно установлен");
        return "login";
    }
}
