package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.MeterReading;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MeterReadingRepositoryTest {

    private MeterReading meterReading;

    @Autowired
    private MeterReadingRepository meterReadingRepository;

    @BeforeEach
    public void setup() {
        meterReading = MeterReading.builder()
                .dateOfCreated(LocalDateTime.now())
                .kitchenHot(100f)
                .kitchenCold(200f)
                .toiletHot(300f)
                .toiletCold(400f)
                .build();
    }

    @Test
    @Order(1)
    public void saveMeterReadingTest() {

        //Arrange

        //Act
        meterReadingRepository.save(meterReading);

        //Assert
        Assertions.assertThat(meterReading.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getMeterReadingsTest() {

        //Arrange
        meterReadingRepository.save(meterReading);

        //Act
        MeterReading meterReading = meterReadingRepository.findById(2L).get();

        //Assert
        org.assertj.core.api.Assertions.assertThat(meterReading.getId()).isEqualTo(2L);
    }

    @Test
    @Order(3)
    public void getListOfMeterReadingsTest() {

        //Arrange
        meterReadingRepository.save(meterReading);

        //Act
        List<MeterReading> meterReadings = meterReadingRepository.findAll();

        //Assert
       Assertions.assertThat(meterReadings.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    public void updateMeterReadingTest() {

        //Arrange
        meterReadingRepository.save(meterReading);
        MeterReading meterReading = meterReadingRepository.findById(4L).get();

        //Act
        meterReading.setKitchenHot(500f);
        meterReadingRepository.save(meterReading);

        //Assert
        Assertions.assertThat(meterReading.getId()).isEqualTo(4L);
    }
}