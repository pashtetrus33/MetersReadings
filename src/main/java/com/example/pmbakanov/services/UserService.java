package com.example.pmbakanov.services;

import com.example.pmbakanov.models.User;
import com.example.pmbakanov.models.enums.Role;
import com.example.pmbakanov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис для работы с пользователями.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    @Value("${DEPLOY_WEBSITE}")
    private String DEPLOY_WEBSITE;

    @Value("${ADMIN_EMAIL}")
    private String ADMIN_EMAIL;

    private final MailSender mailSender;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Создание нового пользователя.
     * @param user пользователь для создания.
     * @return true, если пользователь успешно создан, в противном случае - false.
     */
    public boolean createUser(User user) {
        String email = user.getEmail();
        String address = user.getAddress();
        String flat = user.getFlat();
        if (userRepository.findByEmail(email) != null) return false;
        user.setActive(false);
        user.setAddress(address + " " + flat);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (email.equals(ADMIN_EMAIL)) {
            user.getRoles().add(Role.ROLE_ADMIN);
            log.info("Saving new Admin with email: {}", email);
        } else {
            user.getRoles().add(Role.ROLE_USER);
            log.info("Saving new User with email: {}", email);
        }
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        if (!StringUtils.hasLength(user.getEmail())) {
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

    /**
     * Получение списка всех пользователей.
     * @return список всех пользователей.
     */
    public List<User> list() {
        return userRepository.findAll();
    }

    /**
     * Изменение ролей пользователя.
     * @param user пользователь для изменения ролей.
     * @param form форма с новыми ролями пользователя.
     */
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

    /**
     * Получение пользователя по принципалу.
     * @param principal принципал пользователя.
     * @return пользователь.
     */
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    /**
     * Изменение пароля пользователя.
     * @param user пользователь для изменения пароля.
     * @param form форма с новым паролем.
     */
    public void changeUserPassword(User user, Map<String, String> form) {
        user.setPassword(passwordEncoder.encode(form.get("password")));
        userRepository.save(user);
        for (User item : userRepository.findAllByRoles(Role.ROLE_ADMIN)) {
                mailSender.sendMail(item.getEmail(), "Успешная смена пароля", user.getName() +
                        "\n" + user.getAddress() + "\n" + user.getEmail());
        }
        log.info("Changing password for User with email: {}", user.getEmail());
    }

    /**
     * Сброс пароля пользователя.
     * @param user пользователь для сброса пароля.
     * @return true, если пароль успешно сброшен, в противном случае - false.
     */
    public boolean resetPassword(User user) {
        String email = user.getEmail();
        user = userRepository.findByEmail(email);
        if (userRepository.findByEmail(email) == null) {
            for (User item : userRepository.findAllByRoles(Role.ROLE_ADMIN)) {
                    mailSender.sendMail(item.getEmail(), "Попытка сброса пароля", "Email не найден: " + "\n" + email);
            }
            return false;
        }
        for (User item : userRepository.findAllByRoles(Role.ROLE_ADMIN)) {
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

    /**
     * Удаление пользователя.
     * @param user пользователь для удаления.
     */
    public void deleteUser(User user) {
        log.info("Deleting User with email: {}", user.getEmail());
        userRepository.delete(user);
    }

    /**
     * Активация пользователя по коду активации.
     * @param code код активации пользователя.
     * @return активированный пользователь или null, если пользователь не найден.
     */
    public User activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return null;
        }
        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);
        log.info("Creating User with email: {}", user.getEmail());
        for (User item : userRepository.findAllByRoles(Role.ROLE_ADMIN)) {
            if (!user.isActive())
                mailSender.sendMail(item.getEmail(), "Новая регистрация", user.getName() + "\n" +
                        user.getAddress() + "\n" + user.getEmail());
            else {
                mailSender.sendMail(item.getEmail(), "Успешная проверка кода для сброса пароля", user.getName() + "\n" +
                        user.getAddress() + "\n" + user.getEmail());
            }
        }
        return user;
    }

    /**
     * Отправка электронной почты.
     * @param email адрес электронной почты получателя.
     * @param message сообщение.
     */
    public void sendEmail(String email, String message) {
        if (!email.equals("all@all.ru")) {
            mailSender.sendMail(email, "Информация от системы передачи показаний счетчиков",
                    "Уважаемый(ая) " + userRepository.findByEmail(email).getName() + ".\n" + message);
        } else {
            for (User person : userRepository.findAllByActiveIsTrue()) {
                mailSender.sendMail(person.getEmail(), "Информация от системы передачи показаний счетчиков",
                        "Уважаемый(ая) " + person.getName() + ".\n" + message);
            }
        }
    }

    /**
     * Изменение имени пользователя.
     * @param user пользователь для изменения имени.
     * @param form форма с новым именем.
     */
    public void userRename(User user, Map<String, String> form) {
        user.setName((form.get("name")));
        userRepository.save(user);
        for (User item : userRepository.findAllByRoles(Role.ROLE_ADMIN)) {
            mailSender.sendMail(item.getEmail(), "Успешная смена имени", user.getName() +
                    "\n" + user.getAddress() + "\n" + user.getEmail());
        }
        log.info("Rename user with email: {}", user.getEmail());
    }


    /**
     * Изменение статуса активности пользователя.
     * @param user пользователь для изменения статуса активности.
     * @param form форма с новым статусом активности.
     */
    public void userChangeStatus(User user, Map<String, String> form) {
        if (form.get("status").equals("true")) {
            user.setActive(true);
        } else {
            user.setActive(false);
        }

        userRepository.save(user);
        for (User item : userRepository.findAllByRoles(Role.ROLE_ADMIN)) {

            mailSender.sendMail(item.getEmail(), "Успешная смена статуса активности пользователя ", user.getName() +
                    "\n" + user.getAddress() + "\n" + user.getEmail() + "\n" + user.isActive());
        }
        log.info("User activity status changed with email: {}", user.getEmail());
    }

    /**
     * Изменение ящика электронной почты пользователя.
     * @param user пользователь для изменения ящика электронной почты.
     * @param form форма с новым ящиком электронной почты.
     */
    public void userChangeEmail(User user, Map<String, String> form) {

        user.setEmail(form.get("email"));
        userRepository.save(user);
        for (User item : userRepository.findAllByRoles(Role.ROLE_ADMIN)) {

            mailSender.sendMail(item.getEmail(), "Успешная смена ящика эл. почты пользователя ", user.getName() +
                    "\n" + user.getAddress() + "\n" + user.getEmail() + "\n" + user.isActive());
        }
        log.info("Email changed to: {}", user.getEmail());
    }
}