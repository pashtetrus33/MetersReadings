package com.example.pmbakanov.controllers;

import com.example.pmbakanov.models.ElectricityMeterReading;
import com.example.pmbakanov.models.MeterReading;
import com.example.pmbakanov.models.User;
import com.example.pmbakanov.services.ElectricityMeterReadingsService;
import com.example.pmbakanov.services.MeterReadingService;
import com.example.pmbakanov.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MeterReadingsController {
    private final MeterReadingService meterReadingService;
    private final ElectricityMeterReadingsService electricityMeterReadingsService;
    private final UserService userService;

    /**
     * Метод создания записи счетчиков воды
     *
     * @param model     интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @param meterReading    сущность запись счетчика воды
     * @param principal текущий залогинившийся пользователь
     * @return представление профиля пользователя
     */
    @PostMapping("/meterreading/create")
    public String createMeterReading(Model model, MeterReading meterReading, Principal principal) {
        if (meterReadingService.saveMeterReading(principal, meterReading)) {
            model.addAttribute("successmessage", "Данные успешно переданы");
        } else {
            model.addAttribute("successmessage", "Данные не переданы, предыдущие показания больше текущих");
        }
        model.addAttribute("user", meterReadingService.getUserByPrincipal(principal));

        return "profile";
    }

    /**
     * Метод удаления записи по идентификатору
     *
     * @param id идентификатор записи
     * @return перенаправление на страницу с информацией пользователя
     */
    @PostMapping("/meterreading/delete/{id}")
    public String deleteMeterReading(@PathVariable Long id) {
        Long userId = meterReadingService.getMeterReadingById(id).getUser().getId();
        meterReadingService.deleteMeterReading(id);
        return "redirect:/user/" + userId;
    }

    /**
     * Метод возвращает представление страницы с информацией о внесенных показаниях
     *
     * @param principal текущий залогинившийся пользователь
     * @param model     интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @return представление страницы с информацей о внесенных показаниях
     */
    @GetMapping("/my/meterreadings")
    public String userMeterReadings(Principal principal, Model model) {
        User user = meterReadingService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("meterReadings", user.getMeterReadings());
        model.addAttribute("electricityMeterReadings", user.getElectricityMeterReadings());
        return "my-meterreadings";
    }

    /**
     * Метод возвращает представление страницы со всеми внесенными записями (требуется админ права)
     *
     * @param model     интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @param principal текущий залогинившийся пользователь
     * @return представление страницы со всеми внесенными записями
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_BUH')")
    @GetMapping("/allusersmeterreadings")
    public String allUserMeterReadings(Model model, Principal principal) {

        List<User> userList = userService.list();
        Collections.sort(userList);
        model.addAttribute("meterReadings", meterReadingService.listMeterReadings(null));
        model.addAttribute("electricityMeterReadings", electricityMeterReadingsService.listElectrcityMeterReadings(null)
                .stream()
                .filter(ElectricityMeterReading::doneInCurrentMonth)
                .collect(Collectors.toList()));
        model.addAttribute("users", userList);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("currentMonth", LocalDateTime.now().getMonth());
        model.addAttribute("currentYear", LocalDateTime.now().getYear());
        return "allusersmeterreadings";
    }

    /**
     * Метод экспорта данных в формат Excel документа
     *
     * @param response интерфейс ответа сервлета
     * @throws IOException исключение ввода-вывода
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_BUH')")
    @GetMapping("/export-to-excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + LocalDateTime.now().getMonth() + " " + LocalDateTime.now().getYear() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        meterReadingService.exportCustomerToExcel(response);
    }
}
