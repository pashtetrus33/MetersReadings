package com.example.pmbakanov.controllers;

import com.example.pmbakanov.models.Record;
import com.example.pmbakanov.models.User;
import com.example.pmbakanov.services.RecordService;
import com.example.pmbakanov.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;
    private final UserService userService;

    @GetMapping("/record/{id}")
    public String recordInfo(@PathVariable Long id, Model model, Principal principal) {
        Record record = recordService.getRecordById(id);
        model.addAttribute("user", recordService.getUserByPrincipal(principal));
        model.addAttribute("record", record);
        model.addAttribute("authorRecord", record.getUser());
        return "record-info";
    }

    @PostMapping("/record/create")
    public String createRecord(Record record, Principal principal) throws IOException {
        recordService.saveRecord(principal, record);
        return "redirect:/profile";
    }

    @PostMapping("/record/delete/{id}")
    public String deleteRecord(@PathVariable Long id) {
        Long userId = recordService.getRecordById(id).getUser().getId();
        recordService.deleteRecord(id);
        return "redirect:/user/" + userId;
    }

    @GetMapping("/my/records")
    public String userRecords(Principal principal, Model model) {
        User user = recordService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("records", user.getRecords());
        return "my-records";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    @GetMapping("/allusersrecords")
    public String alluserrecords(Model model, Principal principal) {
        model.addAttribute("records", recordService.listRecords(null));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "alluserrecords";
    }
}
