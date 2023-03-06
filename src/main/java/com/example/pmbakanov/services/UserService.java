package com.example.pmbakanov.services;

import com.example.pmbakanov.models.User;
import com.example.pmbakanov.models.enums.Role;
import com.example.pmbakanov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private MailSender mailSender;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String login = user.getLogin();
        String email = user.getEmail();
        if (userRepository.findByLogin(login) != null) return false;
        if (userRepository.findByEmail(email) != null) return false;
        user.setActive(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getLogin().equals("bakanov")) {
            user.getRoles().add(Role.ROLE_ADMIN);
            log.info("Saving new Admin with login: {}", login);
        } else {
            user.getRoles().add(Role.ROLE_USER);
            log.info("Saving new User with login: {}", login);
        }
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Привет, %s \n" +
                            "Добро пожаловать. Пожалуйста перейдите по ссылке для активации: https://meters.herokuapp.com/activate/%s",
                    user.getName(),
                    user.getActivationCode()
            );
            mailSender.sendMail(user.getEmail(), "Ссылка для активации", message);
        }
        return true;
    }


    public List<User> list() {
        return userRepository.findAll();
    }

    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
                mailSender.sendMail(user.getEmail(), "Смена типа пользователя", Role.valueOf(key).name());
            }
        }
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByLogin(principal.getName());
    }

    public void changeUserPassword(User user, Map<String, String> form) {
        user.setPassword(passwordEncoder.encode(form.get("password")));
        userRepository.save(user);
        log.info("Changing password for User with login: {}", user.getLogin());
    }

    public void deleteUser(User user) {
        log.info("Deleting User with login: {}", user.getLogin());
        userRepository.delete(user);
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);

        return true;
    }
}
