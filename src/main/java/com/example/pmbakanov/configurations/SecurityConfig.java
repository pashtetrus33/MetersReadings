package com.example.pmbakanov.configurations;

import com.example.pmbakanov.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Конфигурация безопасности веб-приложения.
 * Этот класс конфигурации активирует безопасность веб-приложения и устанавливает различные правила безопасности,
 * такие как ограничение доступа к определенным URL-адресам, настройка страницы входа в систему и обработка
 * аутентификации пользователей.
 */
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService userDetailsService;

    /**
     * Конфигурирует доступ к различным URL-адресам в веб-приложении.
     *
     * @param http объект HttpSecurity, используемый для настройки безопасности HTTP
     * @throws Exception если возникает ошибка при настройке безопасности
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/images/**", "/static/**", "/activate/*", "/registration", "/reset/**" , "/newpassword")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/")
                .defaultSuccessUrl("/profile")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    /**
     * Конфигурирует аутентификацию пользователей.
     *
     * @param auth объект AuthenticationManagerBuilder, используемый для настройки аутентификации
     * @throws Exception если возникает ошибка при настройке аутентификации
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * Возвращает экземпляр PasswordEncoder для хеширования паролей пользователей.
     *
     * @return экземпляр PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
