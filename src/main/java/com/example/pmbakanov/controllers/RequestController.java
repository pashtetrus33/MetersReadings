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


    @PostMapping("/request/create")
    public String createRequest(@RequestParam(value = "file1") MultipartFile file1,
                                @RequestParam(value = "file2") MultipartFile file2,
                                Request request, Principal principal, Model model) throws IOException {
        requestService.saveRequest(principal, request, file1, file2);
        model.addAttribute("user", requestService.getUserByPrincipal(principal));
        model.addAttribute("successrequest", "Заявка успешно передана");
        return "profile";
    }

    @PostMapping("/request/delete/{id}")
    public String deleteRequest(@PathVariable Long id) {
        Long userId = requestService.getRequestById(id).getUser().getId();
        requestService.deleteRequest(id);
        return "redirect:/user/" + userId;
    }

    @GetMapping("/my/requests")
    public String userRequests(Principal principal, Model model) {
        User user = requestService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("requests", user.getRequests());
        return "my-requests";
    }

    @PostMapping("/request/statusedit")
    public String requestStatusEdit(@RequestParam Long requestId, @RequestParam Map<String, String> form) {
        Request request = requestService.getRequestById(requestId);
        requestService.changeRequestStatus(request, form);
        requestService.changeRequestExecutor(request, form);

        return "redirect:/allusersrequests";
    }

    @GetMapping("/request/statusedit/{request}")
    public String userEdit(@PathVariable("request") Request request, Model model) {
        model.addAttribute("request", request);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("executors", ExecutorName.values());
        model.addAttribute("currentstatus", request.getStatus());
        return "statusrequest-edit";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    @GetMapping("/allusersrequests")
    public String allusersrequests(Model model, Principal principal) {
        model.addAttribute("requests", requestService.listRequests(null));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "alluserrequests";
    }
}
