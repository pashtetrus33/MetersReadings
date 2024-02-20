package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.User;
import com.example.pmbakanov.models.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {

    private User user;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        user = User.builder()
                .email("test@test.com")
                .password("password")
                .address("test address")
                .build();
    }

    @Test
    @Order(1)
    public void saveUserTest() {

        //Arrange

        //Act
        userRepository.save(user);

        //Assert
        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getUserTest() {

        //Arrange
        userRepository.save(user);

        //Act
        User user = userRepository.findById(2L).get();

        //Assert
        Assertions.assertThat(user.getId()).isEqualTo(2L);
    }

    @Test
    @Order(3)
    public void getListOfUsersTest() {

        //Arrange
        userRepository.save(user);

        //Act
        List<User> users = userRepository.findAll();

        //Assert
        Assertions.assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    public void updateUserTest() {

        //Arrange
        userRepository.save(user);
        User user = userRepository.findById(4L).get();

        //Act
        user.setEmail("newemail@test.com");
        userRepository.save(user);

        //Assert
        Assertions.assertThat(user.getId()).isEqualTo(4L);
    }

    @Test
    @Order(5)
    public void deleteUserTest() {
        //Arrange
        userRepository.save(user);
        User user = userRepository.findById(5L).get();
        //Act
        userRepository.delete(user);

        User optionalUser = userRepository.findByEmail("newemail@test.com");
        //Assert
        Assertions.assertThat(optionalUser).isNull();
    }

    @Test
    @Order(6)
    void findByActivationCode() {

        //Arrange
        String testActivationCode = "343463";
        user.setActivationCode(testActivationCode);
        userRepository.save(user);

        //Act
        User user1 = userRepository.findByActivationCode(testActivationCode);

        //Assert
        Assertions.assertThat(user1).isNotNull();
        Assertions.assertThat(user1.getActivationCode()).isEqualTo(testActivationCode);

    }

    @Test
    @Order(7)
    void findByEmail() {

        //Arrange
        String testEmail = "email@gmailcom";
        user.setEmail(testEmail);
        userRepository.save(user);

        //Act
        User user1 = userRepository.findByEmail(testEmail);

        //Assert
        Assertions.assertThat(user1).isNotNull();
        Assertions.assertThat(user1.getEmail()).isEqualTo(testEmail);
    }

    @Test
    @Order(8)
    void findByAddress() {
        //Arrange
        String testAddress = "testAdress";
        user.setEmail(testAddress);
        userRepository.save(user);

        //Act
        User user1 = userRepository.findByEmail(testAddress);

        //Assert
        Assertions.assertThat(user1).isNotNull();
        Assertions.assertThat(user1.getEmail()).isEqualTo(testAddress);
    }

    @Test
    @Order(9)
    void findAllByActiveIsTrue() {
        //Arrange
        user.setActive(true);
        userRepository.save(user);

        //Act
        List<User> users = userRepository.findAllByActiveIsTrue();

        //Assert
        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    @Order(10)
    void findAllByRoles() {
        //Arrange
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);

        user.setRoles(roles);
        userRepository.save(user);

        //Act
        List<User> users = userRepository.findAllByRoles(Role.ROLE_USER);
        //Assert
        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(users.size()).isGreaterThan(0);
    }
}