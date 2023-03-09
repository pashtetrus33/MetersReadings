package com.example.pmbakanov.controllers;

import com.example.pmbakanov.models.Request;
import com.example.pmbakanov.models.User;
import com.example.pmbakanov.models.enums.Role;
import com.example.pmbakanov.models.enums.Status;
import com.example.pmbakanov.services.RequestService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/request/{id}")
    public String requestInfo(@PathVariable Long id, Model model, Principal principal) {
        Request request = requestService.getRequestById(id);
        model.addAttribute("user", requestService.getUserByPrincipal(principal));
        model.addAttribute("request", request);
        model.addAttribute("authorRequest", request.getUser());
        model.addAttribute("images", request.getImages());
        return "request-info";
    }

//    @PostMapping("/request/create")
//    public String createRequest(@RequestParam("file1") MultipartFile file1,
//                                @RequestParam("file2") MultipartFile file2,
//                                Request request, Principal principal) throws IOException {
//        requestService.saveRequest(principal, request, file1, file2);
//        return "redirect:/profile";
//    }

    @PostMapping("/request/create")
    public String createRequest(Request request, Principal principal) throws IOException {
        requestService.saveRequest(principal, request);
        return "redirect:/profile";
    }

    @PostMapping("/request/delete/{id}")
    public String deleteRequest(@PathVariable Long id) {
        Long userId = requestService.getRequestById(id).getUser().getId();
        requestService.deleteRequest(id);
        return "redirect:/allusersrequests";
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
        requestService.changeRequestStatus(requestService.getRequestById(requestId), form);
        return "redirect:/profile";
    }

    @GetMapping("/request/statusedit/{user}")
    public String userEdit(@PathVariable("user") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("statuses", Status.values());
        return "statusrequest-edit";
    }
}
