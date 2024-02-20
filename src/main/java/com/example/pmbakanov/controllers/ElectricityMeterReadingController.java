package com.example.pmbakanov.controllers;

import com.example.pmbakanov.models.ElectricityMeterReading;
import com.example.pmbakanov.services.ElectricityMeterReadingsService;
import com.example.pmbakanov.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_TECHNICIAN')")
public class ElectricityMeterReadingController {
    private final ElectricityMeterReadingsService electricityMeterReadingsService;
    private final UserService userService;

    /**
     * Метод возвращает представление для внесения данных счетчиков электричества для всех жильцов разом
     *
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @param principal ттекущий залогинившийся пользователь
     * @return представление страницы для внесения данных счетчиков электричества
     */
    @GetMapping("/allelectricity")
    public String allUserElectricity(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "allelectricity";
    }

    /**
     * Метод удаления записи показания электричетсва
     *
     * @param id идентификатор записи
     * @return перенаправление на представелние стариницы с показаниями всех пользователей за месяц
     */
    @PostMapping("/electricitymeterreading/delete/{id}")
    public String deleteElectricityMeterReading(@PathVariable Long id) {
        electricityMeterReadingsService.deleteElectricityMeterReading(id);
        return "redirect:/allusersmeterreadings";
    }


    /**
     * Метод получения переданных счетчиков электричества из формы
     *
     * @param model интерфейс фреймворка для упаковки аттрибутов и передачи в представление
     * @param electricityMeterReading запись счетчика электричества
     * @param building1 квартира из здания 1
     * @param building2 квартира из здания 2
     * @param school квартира из школьно-жилого здания
     * @param principal текущий залогинившийся пользователь
     * @return представление страницы с формой для ввода счетчиков электричества
     */
    @PostMapping("/allelectricity")
    public String electricityDataCreate(Model model, ElectricityMeterReading electricityMeterReading, String building1, String building2, String school, Principal principal) {
        electricityMeterReading.setDataProviderName(userService.getUserByPrincipal(principal).getName());
        if (electricityMeterReadingsService.saveElectricityMeterReading(building1, building2, school, electricityMeterReading)) {
            return "redirect:/allelectricity";
        } else {
            model.addAttribute("successmessage", "Данные электричества не переданы, предыдущие показания больше текущих или введен некорректный адрес, нажмите кнопку обновить");
            model.addAttribute("user", userService.getUserByPrincipal(principal));
            return "allelectricity";
        }
    }
}
