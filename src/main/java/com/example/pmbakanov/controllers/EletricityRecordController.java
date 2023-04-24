package com.example.pmbakanov.controllers;

import com.example.pmbakanov.models.ElectricityRecord;
import com.example.pmbakanov.services.ElectricityRecordService;
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
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_TECHICIAN')")
public class EletricityRecordController {
    private final ElectricityRecordService electricityRecordService;
    private final UserService userService;


    @PostMapping("/eletricityrecord/delete/{user}/{id}")
    public String deleteEletricityRecord(@PathVariable Long user, @PathVariable Long id) {
        Long userId = electricityRecordService.getElectricityRecordById(id).getUser().getId();
        electricityRecordService.deleteElectricityRecord(id);
        return "redirect:/user/{user}";
    }


    @GetMapping("/allelectricity")
    public String allUserElectricity(Model model, Principal principal) {
        model.addAttribute("users", userService.list());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "allelectricity";
    }

    @PostMapping("/allelectricity")
    public String electricityDataCreate(Model model, ElectricityRecord electricityRecord, String building, String flat, Principal principal) {
        electricityRecord.setDataProviderName(userService.getUserByPrincipal(principal).getName());
        if (electricityRecordService.saveElectricityRecord(building, flat, electricityRecord)) {
            model.addAttribute("successmessage", "Данные электричества успешно переданы, нажмите кнопку обновить");
        } else {
            model.addAttribute("successmessage", "Данные электричества не переданы, предыдущие показания больше текущих, нажмите кнопку обновить");
        }
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("users", userService.list());
        return "allelectricity";
    }
}
