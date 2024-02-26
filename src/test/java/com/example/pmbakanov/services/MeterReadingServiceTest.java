package com.example.pmbakanov.services;

import com.example.pmbakanov.models.MeterReading;
import com.example.pmbakanov.models.User;
import com.example.pmbakanov.repositories.ElectricityMeterReadingRepository;
import com.example.pmbakanov.repositories.MeterReadingRepository;
import com.example.pmbakanov.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@SpringBootTest
class MeterReadingServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MailSender mailSender;

    @Mock
    private MeterReadingRepository meterReadingRepository;
    @Mock
    private ElectricityMeterReadingRepository electricityMeterReadingRepository;

    @InjectMocks
    private MeterReadingService meterReadingService;


    @Test
    void listMeterReadingsFindById() {
        //Arrange
        String testId = "testId";
        when(meterReadingRepository.findById(testId)).thenReturn((List.of(new MeterReading())));
        //Act
        List<MeterReading> meterReadingList = meterReadingService.listMeterReadings(testId);

        //Assert
        Assertions.assertThat(meterReadingList.size()).isGreaterThan(0);
        verify(meterReadingRepository, times(1)).findById(testId);
    }

    @Test
    void listMeterReadingsFindAll() {
        //Arrange;
        when(meterReadingRepository.findAll()).thenReturn((List.of(new MeterReading())));
        //Act
        List<MeterReading> meterReadingList = meterReadingService.listMeterReadings(null);

        //Assert
        Assertions.assertThat(meterReadingList.size()).isGreaterThan(0);
        verify(meterReadingRepository, times(1)).findAll();
    }


    @Test
    void saveMeterReading() {
    }

    @Test
    void getUserByPrincipal() {
        User user = meterReadingService.getUserByPrincipal(null);

        //Assert
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getEmail()).isNull();
        Assertions.assertThat(user.getId()).isNull();
    }

    @Test
    void deleteMeterReadingIdNotFound() {
        when(meterReadingRepository.findById(1L)).thenReturn(Optional.empty());

        meterReadingService.deleteMeterReading(1L);

        verify(meterReadingRepository, times(0)).deleteById(any());
    }

    @Test
    void deleteMeterReading() {
        //Arrange
        MeterReading meterReading = new MeterReading();
        meterReading.setId(1L);
        when(meterReadingRepository.findById(1L)).thenReturn(Optional.of(meterReading));
        //Act
        meterReadingService.deleteMeterReading(1L);
        //Assert
        verify(meterReadingRepository, times(1)).delete(meterReading);
    }

    @Test
    void getMeterReadingById() {
        MeterReading meterReading = new MeterReading();
        meterReading.setId(1L);
        when(meterReadingRepository.findById(1L)).thenReturn(Optional.of(meterReading));
        meterReading = meterReadingService.getMeterReadingById(1L);
        //Assert
        Assertions.assertThat(meterReading).isNotNull();
        Assertions.assertThat(meterReading.getId()).isEqualTo(1L);
    }

    @Test
    void getMeterReadingByIdReturnsNull() {

        when(meterReadingRepository.findById(1L)).thenReturn(Optional.empty());
        MeterReading meterReading = meterReadingService.getMeterReadingById(1L);
        //Assert
        Assertions.assertThat(meterReading).isNull();
    }
}