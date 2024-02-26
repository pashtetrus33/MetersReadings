package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.Request;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RequestRepositoryTest {

    private Request request;

    @Autowired
    private RequestRepository requestRepository;

    @BeforeEach
    public void setup() {
        request = new Request();
    }
    @Test
    void findById() {

        //Arrange
        requestRepository.save(request);

        //Act
        Request request = requestRepository.findById(1L).get();

        //Assert
        Assertions.assertThat(request.getId()).isEqualTo(1L);
    }
}