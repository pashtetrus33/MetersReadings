package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.ElectricityMeterReading;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ElectricityMeterReadingRepositoryTest {

    private ElectricityMeterReading electricityMeterReading;

    @Autowired
    private ElectricityMeterReadingRepository electricityMeterReadingRepository;

    @BeforeEach
    public void setup() {
        electricityMeterReading = new ElectricityMeterReading();
    }
    @Test
    void findById() {

        //Arrange
        electricityMeterReadingRepository.save(electricityMeterReading);

        //Act
        ElectricityMeterReading electricityMeterReading = electricityMeterReadingRepository.findById(1L).get();

        //Assert
        Assertions.assertThat(electricityMeterReading.getId()).isEqualTo(1L);
    }
}