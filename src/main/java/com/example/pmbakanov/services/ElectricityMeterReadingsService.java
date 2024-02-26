package com.example.pmbakanov.services;

import com.example.pmbakanov.models.ElectricityMeterReading;
import com.example.pmbakanov.models.User;
import com.example.pmbakanov.models.enums.Role;
import com.example.pmbakanov.repositories.ElectricityMeterReadingRepository;
import com.example.pmbakanov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElectricityMeterReadingsService {
    private final ElectricityMeterReadingRepository electricityMeterReadingRepository;
    private final UserRepository userRepository;

    public List<ElectricityMeterReading> listElectrcityMeterReadings(String id) {
        if (id != null) return electricityMeterReadingRepository.findById(id);
        return electricityMeterReadingRepository.findAll();
    }

    /**
     * Метод сохранения записи счетчика электричества
     *
     * @param building1         здание 1
     * @param building2         задние 2
     * @param school            школьно-жилое здание
     * @param electricityMeterReading запись счечика электричества
     * @return булевое значение успеха сохранения записи
     */
    public boolean saveElectricityMeterReading(String building1, String building2, String school, ElectricityMeterReading electricityMeterReading) {
        if (building1.equals("empty") && building2.equals("empty") && school.equals("empty")) {
            return false;
        }
        if ((!building1.equals("empty") && !building2.equals("empty")) || (!building1.equals("empty") && !school.equals("empty"))
                || (!building2.equals("empty") && !school.equals("empty"))) {
            return false;
        }
        String address = null;
        String flat = null;
        if (!building1.equals("empty")) {
            address = "Жилой дом №1 " + building1;
            flat = building1;
        } else if (!building2.equals("empty")) {
            address = "Жилой дом №2 " + building2;
            flat = building2;
        } else {
            address = "Школьно-жилой дом " + school;
            flat = school;
        }


        User user = userRepository.findByAddress(address);
        if (user == null) {
            user = new User();
            user.setAddress(address);
            user.setFlat(flat);
            user.setName("Запись без пользователя");
            user.setActive(false);
            Random random = new Random();
            String randomEmail = random.nextInt(70) + "@test.test";
            if (userRepository.findByEmail(randomEmail) != null) {
                randomEmail = random.nextInt(70) + randomEmail;
                user.setEmail(randomEmail);
            } else {
                user.setEmail(randomEmail);
            }
            user.getRoles().add(Role.ROLE_USER);
            userRepository.save(user);
        }
        if (user.areElectricityMeterReadings()) {
            if (user.getLastElectricityMeterReading().getElectricity() > electricityMeterReading.getElectricity()) {
                return false;
            }
        }
        electricityMeterReading.setUser(user);
        electricityMeterReadingRepository.save(electricityMeterReading);
        return true;
    }

    public void deleteElectricityMeterReading(Long id) {
        ElectricityMeterReading electricityMeterReading = electricityMeterReadingRepository.findById(id)
                .orElse(null);
        if (electricityMeterReading != null) {
            electricityMeterReadingRepository.delete(electricityMeterReading);
        } else {
            log.error("Electricity MeterReading with id = {} is not found", id);
        }
    }

    public ElectricityMeterReading getElectricityMeterReadingById(Long id) {
        return electricityMeterReadingRepository.findById(id).orElse(null);
    }
}
