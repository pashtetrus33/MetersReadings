package com.example.pmbakanov.services;

import com.example.pmbakanov.models.Image;
import com.example.pmbakanov.models.Request;
import com.example.pmbakanov.models.User;
import com.example.pmbakanov.repositories.RequestRepository;
import com.example.pmbakanov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public List<Request> listRequests(String id) {
        if (id != null) return requestRepository.findById(id);
        return requestRepository.findAll();
    }

    public void saveRequest(Principal principal, Request request, MultipartFile file1, MultipartFile file2) throws IOException {
        request.setUser(getUserByPrincipal(principal));
        Image image1;
        Image image2;
        if(file1.getSize() !=0) {
            image1 = toImageEntity(file1);
            request.addImageToRequest(image1);
        }
        if(file2.getSize() !=0){
            image2 = toImageEntity(file2);
            request.addImageToRequest(image2);
        }

        log.info("Saving new Request. Description: {}", request.getDescription());
        requestRepository.save(request);
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByLogin(principal.getName());
    }


    public void deleteRequest(Long id) {
        Request request = requestRepository.findById(id)
                .orElse(null);
        if (request != null) {
            requestRepository.delete(request);
            log.info("Request with id = {} was deleted", id);

        } else {
            log.error("Request with id = {} is not found", id);
        }
    }

    public Request getRequestById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    public void statusRequest(Long id) {
        Request request = requestRepository.findById(id)
                .orElse(null);
        request.setStatus("В работе");
        requestRepository.save(request);
    }
}
