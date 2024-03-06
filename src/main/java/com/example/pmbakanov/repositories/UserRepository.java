package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.User;
import com.example.pmbakanov.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с пользователями.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по коду активации.
     * @param code Код активации пользователя.
     * @return Пользователь с указанным кодом активации.
     */
    User findByActivationCode(String code);

    /**
     * Находит пользователя по электронной почте.
     * @param email Электронная почта пользователя.
     * @return Пользователь с указанной электронной почтой.
     */
    User findByEmail(String email);

    /**
     * Находит пользователя по адресу.
     * @param address Адрес пользователя.
     * @return Пользователь с указанным адресом.
     */
    User findByAddress(String address);

    /**
     * Находит всех активных пользователей.
     * @return Список всех активных пользователей.
     */
    List<User> findAllByActiveIsTrue();

    /**
     * Находит всех пользователей с определенной ролью.
     * @param role Роль пользователей.
     * @return Список всех пользователей с указанной ролью.
     */
    List<User> findAllByRoles(Role role);
}

