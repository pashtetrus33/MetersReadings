package com.example.pmbakanov.models.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Перечисление Role представляет роли пользователей в системе.
 * Каждая роль связана с определенными правами доступа и представлена в виде строки.
 */
public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_SUPERVISOR, ROLE_BUH, ROLE_TECHNICIAN, ROLE_ADMIN;

    /**
     * Получает наименование роли.
     *
     * @return наименование роли
     */
    @Override
    public String getAuthority() {
        return name();
    }
}
