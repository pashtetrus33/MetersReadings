package com.example.pmbakanov.services;

import com.example.pmbakanov.models.User;
import com.example.pmbakanov.models.enums.Role;
import com.example.pmbakanov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    static final String DEPLOY_WEBSITE = "http://185.87.51.45";
    static final String DEPLOY_WEBSITE_REDIRECT = "http://chilemeters.ru";

    private final MailSender mailSender;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String email = user.getEmail();
        String address = user.getAddress();
        String flat = user.getFlat();
        if (userRepository.findByEmail(email) != null) return false;
        user.setActive(false);
        user.setAddress(address + " " + flat);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (email.equals("pashtet_rus@mail.ru")) {
            user.getRoles().add(Role.ROLE_ADMIN);
            log.info("Saving new Admin with email: {}", email);
        } else {
            user.getRoles().add(Role.ROLE_USER);
            log.info("Saving new User with email: {}", email);
        }
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Добро пожаловать, %s. \n" +
                            "Пожалуйста перейдите по ссылке для активации: " + DEPLOY_WEBSITE + "/activate/%s",
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
        for (String key : form.values()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
                mailSender.sendMail(user.getEmail(), "Смена типа пользователя", Role.valueOf(key).name());
            }
        }
        userRepository.save(user);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public void changeUserPassword(User user, Map<String, String> form) {
        user.setPassword(passwordEncoder.encode(form.get("password")));
        userRepository.save(user);
        for (User item : userRepository.findAll()) {
            if (item.isAdmin())
                mailSender.sendMail(item.getEmail(), "Успешная смена пароля", user.getName() +
                        "\n" + user.getAddress() + "\n" + user.getEmail());
        }
        log.info("Changing password for User with email: {}", user.getEmail());
    }

    public boolean resetPassword(User user) {
        String email = user.getEmail();
        user = userRepository.findByEmail(email);
        if (userRepository.findByEmail(email) == null) {
            for (User item : userRepository.findAll()) {
                if (item.isAdmin())
                    mailSender.sendMail(item.getEmail(), "Попытка сброса пароля", "Email не найден: " + "\n" + email);
            }
            return false;
        }
        for (User item : userRepository.findAll()) {
            if (item.isAdmin())
                mailSender.sendMail(item.getEmail(), "Попытка сброса пароля", "Email найден: " + "\n" + email);
        }
        log.info("Changing password for User with email: {}", user.getEmail());
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        String message = String.format(
                "Добрый день, %s. \n" +
                        "Пожалуйста перейдите по ссылке для сброса пароля: " + DEPLOY_WEBSITE + "/reset/%s",
                user.getName(),
                user.getActivationCode()
        );
        mailSender.sendMail(user.getEmail(), "Сброс пароля", message);
        return true;
    }

    public void deleteUser(User user) {
        log.info("Deleting User with email: {}", user.getEmail());
        userRepository.delete(user);
    }

    public User activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return null;
        }
        user.setActivationCode(null);
        for (User item : userRepository.findAll()) {
            if (item.isAdmin() && (!user.isActive()))
                mailSender.sendMail(item.getEmail(), "Новая регистрация", user.getName() + "\n" +
                        user.getAddress() + "\n" + user.getEmail());
            else if (item.isAdmin() && (user.isActive())) {
                mailSender.sendMail(item.getEmail(), "Успешная проверка кода для сброса пароля", user.getName() + "\n" +
                        user.getAddress() + "\n" + user.getEmail());
            }
        }
        user.setActive(true);
        userRepository.save(user);
        log.info("Creating User with email: {}", user.getEmail());

        return user;
    }

    public void sendEmail(String email, String message) {
        if (!email.equals("all@all.ru")) {
            mailSender.sendMail(email, "Информация от системы передачи показаний счетчиков",
                    "Уважаемый(ая) " + userRepository.findByEmail(email).getName() + ".\n" + message);
        } else {
            for (User person : userRepository.findAll()) {
                mailSender.sendMail(person.getEmail(), "Информация от системы передачи показаний счетчиков",
                        "Уважаемый(ая) " + person.getName() + ".\n" + message);
            }
        }
    }

    public void userRename(User user, Map<String, String> form) {
        user.setName((form.get("name")));
        userRepository.save(user);
        for (User item : userRepository.findAll()) {
            if (item.isAdmin())
                mailSender.sendMail(item.getEmail(), "Успешная смена имени", user.getName() +
                        "\n" + user.getAddress() + "\n" + user.getEmail());
        }
        log.info("Rename user with email: {}", user.getEmail());
    }
}
