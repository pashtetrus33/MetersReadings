package com.example.pmbakanov.controllers;

import com.example.pmbakanov.models.Request;
import com.example.pmbakanov.models.User;
import com.example.pmbakanov.models.enums.ExecutorName;
import com.example.pmbakanov.models.enums.Status;
import com.example.pmbakanov.services.RequestService;
import com.example.pmbakanov.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;
    private final UserService userService;

     /**
     * Метод создания заявки на тех.работы
     * @param file1 прикрепленное фото1
     * @param file2 прикрепленное фото2
     * @param request сущность заявки на тех. работы
     * @param principal текущий залогинившийся пользователь
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @return представление профиля пользователя
     * @throws IOException исключение ввода вывода
     */
    @PostMapping("/request/create")
    public String createRequest(@RequestParam(value = "file1") MultipartFile file1,
                                @RequestParam(value = "file2") MultipartFile file2,
                                Request request, Principal principal, Model model) throws IOException {
        requestService.saveRequest(principal, request, file1, file2);
        model.addAttribute("user", requestService.getUserByPrincipal(principal));
        model.addAttribute("successrequest", "Заявка успешно передана");
        return "profile";
    }

    /**
     * Метод удаления заявки на тех. работы
     * @param id идентификаторы заявки
     * @return перенаправление на страницу информации о данных пользователя
     */
    @PostMapping("/request/delete/{id}")
    public String deleteRequest(@PathVariable Long id) {
        Long userId = requestService.getRequestById(id).getUser().getId();
        requestService.deleteRequest(id);
        return "redirect:/user/" + userId;
    }

    /**
     * Метод отображения всех заявок пользователя
     * @param principal текущий залогинившийся пользователь
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @return представление всех заявок текущего пользователя
     */
    @GetMapping("/my/requests")
    public String userRequests(Principal principal, Model model) {
        User user = requestService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("requests", user.getRequests());
        return "my-requests";
    }

    /**
     * Метод изменения статуса заявки на тех. работы
     * @param requestId идентификаторы заявки
     * @param form форма из представления
     * @return перенаправление на представление всех заявок всех пользователей
     */
    @PostMapping("/request/statusedit")
    public String requestStatusEdit(@RequestParam Long requestId, @RequestParam Map<String, String> form) {
        Request request = requestService.getRequestById(requestId);
        requestService.changeRequestStatus(request, form);
        return "redirect:/allusersrequests";
    }

    /**
     * Метод изменения статуса заявки на тех. работы
     * @param request сущность заявки на тех. работы
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @return представление изменения статуса и исполнителя заявки на тех. работ
     */
    @GetMapping("/request/statusedit/{request}")
    public String userEdit(@PathVariable("request") Request request, Model model) {
        model.addAttribute("request", request);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("executors", ExecutorName.values());
        model.addAttribute("currentstatus", request.getStatus());
        return "statusrequest-edit";
    }

    /**
     * Метод отображения всех заявок на тех. работы всех пользователей
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @param principal текущий залогинившийся пользователь
     * @return представление со всеми заявками на тех. работы всех пользователей
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    @GetMapping("/allusersrequests")
    public String allusersrequests(Model model, Principal principal) {
        model.addAttribute("requests", requestService.listRequests(null));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "alluserrequests";
    }
}
