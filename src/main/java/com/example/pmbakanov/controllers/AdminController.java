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

    /**
     * Метод возвращает представление страницы администрирования
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @param principal текущий пользователь
     * @return представелние панели администартора
     */
    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        List<User> userList = userService.list();
        Collections.sort(userList);
        model.addAttribute("users", userList);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "admin";
    }

    /**
     * Метод возвращает представление страницы редактирования пользователя
     * @param user обьект пользователя, приходящий из URL
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @param principal текущий пользователь
     * @return представелние страницы редактирования пользователя
     */
    @GetMapping("/admin/user/edit/{user}")
    public String userEdit(@PathVariable("user") User user, Model model, Principal principal) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    /**
     * Метод изменения роли пользователя
     * @param user пользователь, получаемый фреймвороком с помощью идентификатора пользователя
     * @param form форма, приходящая с POST запросом
     * @return перенаправление на страницу администрирования
     */
    @PostMapping("/admin/user/edit")
    public String userEdit(@RequestParam("userId") User user, @RequestParam Map<String, String> form) {
        userService.changeUserRoles(user, form);
        return "redirect:/admin";
    }

    /**
     * Метод изменения пароля пользователя
     * @param user пользователь, получаемый фреймвороком с помощью идентификатора пользователя
     * @param form форма, приходящая с POST запросом
     * @return перенаправление на страницу администрирования
     */
    @PostMapping("/admin/user/password_change")
    public String userPasswordChange(@RequestParam("userId") User user, @RequestParam Map<String, String> form) {
        userService.changeUserPassword(user, form);
        return "redirect:/admin";
    }

    /**
     * Метод изменения имени пользователя
     * @param user пользователь, получаемый фреймвороком с помощью идентификатора пользователя
     * @param form форма, приходящая с POST запросом
     * @return перенаправление на страницу администрирования
     */
    @PostMapping("/admin/user/rename")
    public String userRename(@RequestParam("userId") User user, @RequestParam Map<String, String> form) {
        userService.userRename(user, form);
        return "redirect:/admin";
    }

    /**
     * Метод удаления пользователя
     * @param user пользователь, получаемый фреймвороком с помощью идентификатора пользователя
     * @return перенаправление на страницу администрирования
     */
    @PostMapping("/admin/user/delete")
    public String userDelete(@RequestParam("userId") User user) {
        userService.deleteUser(user);
        return "redirect:/admin";
    }

    /**
     * Метод отправки электронной почты
     * @param email адрес эл. почты из формы
     * @param message сообщение из формы
     * @param user пользователь, необходим для корректного отображения представления
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @return возвращает страницу администрирования
     */
    @PostMapping("/admin/sendmail")
    public String sendMail(@RequestParam(value = "email") String email, @RequestParam("message") String message, User user, Model model) {
        userService.sendEmail(email, message);
        model.addAttribute("loginalert", "Письмо успешно отправлено");
        List<User> userList = userService.list();
        Collections.sort(userList);
        model.addAttribute("users", userList);
        return "admin";
    }

    /**
     * Метод возвращает представление страницы информации о пользователе
     * @param user обьект пользователя, приходящий из URL
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @param principal текущий пользователь
     * @return представление с информацией о пользователе
     */
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
