package com.example.pmbakanov.services;

import com.example.pmbakanov.models.User;
import com.example.pmbakanov.models.enums.Role;
import com.example.pmbakanov.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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

    @DisplayName("JUnit test for saveUser method positive")
    @Test
    void givenUserObject_whenSaveUser_thenReturnTrue() {
        // given - precondition or setup
        given(userRepository.findByEmail(user.getEmail())).willReturn(null);
        given(userRepository.save(user)).willReturn(user);
        given(passwordEncoder.encode(user.getPassword())).willReturn(user.getPassword());
        //doNothing().when(mailSender).sendMail(anyString(), anyString(), anyString());


        // when -  action or the behaviour that we are going test
        boolean isUserCreated = userService.createUser(user);

        // then - verify the output
        assertTrue(isUserCreated);
        assertEquals(Optional.of(Role.ROLE_USER), user.getRoles().stream().findFirst());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @DisplayName("JUnit test for saveUser method negative")
    @Test
    void givenUserObject_whenSaveExistingUser_thenReturnFalse() {
        // given - precondition or setup
        given(userRepository.findByEmail(user.getEmail())).willReturn(user);

        // when -  action or the behaviour that we are going test
        boolean isUserCreated = userService.createUser(user);

        // then - verify the output
        assertFalse(isUserCreated);
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("JUnit test for saveUser method with empty email")
    @Test
    void givenUserObject_whenSaveUserWithEmptyEmail_thenReturnFalse() {
        // given - precondition or setup
        user.setEmail("");
        given(userRepository.findByEmail(user.getEmail())).willReturn(null);
        given(userRepository.save(user)).willReturn(user);
        given(passwordEncoder.encode(user.getPassword())).willReturn(user.getPassword());


        // when -  action or the behaviour that we are going test
        boolean isUserCreated = userService.createUser(user);

        // then - verify the output
        assertTrue(isUserCreated);
        assertEquals(Optional.of(Role.ROLE_USER), user.getRoles().stream().findFirst());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @DisplayName("JUnit test for getAllUsers method")
    @Test
    void givenUserList_whenGetAllUsers_thenReturnUserList() {
        // given - precondition or setup

        User user2 = User.builder()
                .id(2L)
                .name("John")
                .email("johnmail@gmail.com")
                .password("qwerty")
                .address("Moscow city")
                .flat("122")
                .roles(new HashSet<>())
                .build();

        given(userRepository.findAll()).willReturn(List.of(user, user2));
        // when -  action or the behaviour that we are going test
        List<User> userList = userService.list();

        // then - verify the output
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(2);
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