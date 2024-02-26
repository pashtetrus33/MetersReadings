package com.example.pmbakanov.services;

import com.example.pmbakanov.models.User;
import com.example.pmbakanov.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Value("${DEPLOY_WEBSITE}")
    private String DEPLOY_WEBSITE;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MailSender mailSender;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Pavel")
                .email("testemail@gmail.com")
                .password("password")
                .address("Vladimir City")
                .flat("22")
                .roles(new HashSet<>())
                .build();
    }

    @Test
    void createUser() {
        // given - precondition or setup
        given(userRepository.findByEmail(user.getEmail())).willReturn(null);
        given(userRepository.save(user)).willReturn(user);
        given(passwordEncoder.encode(user.getPassword())).willReturn(user.getPassword());
        doNothing().when(mailSender).sendMail(anyString(), anyString(), anyString());


        // when -  action or the behaviour that we are going test
        boolean isUserCreated = userService.createUser(user);

        // then - verify the output
        assertTrue(isUserCreated);
    }

    @Test
    void list() {
    }

    @Test
    void changeUserRoles() {
    }

    @Test
    void getUserByPrincipal() {
    }

    @Test
    void changeUserPassword() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void activateUser() {
    }

    @Test
    void sendEmail() {
    }

    @Test
    void userRename() {
    }

    @Test
    void userChangeStatus() {
    }

    @Test
    void userChangeEmail() {
    }
}