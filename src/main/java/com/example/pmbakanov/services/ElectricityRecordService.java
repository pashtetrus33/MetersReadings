package com.example.pmbakanov.services;

import com.example.pmbakanov.models.ElectricityRecord;
import com.example.pmbakanov.models.User;
import com.example.pmbakanov.models.enums.Role;
import com.example.pmbakanov.repositories.ElectricityRecordRepository;
import com.example.pmbakanov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElectricityRecordService {
    private final ElectricityRecordRepository electricityRecordRepository;
    private final UserRepository userRepository;

    public List<ElectricityRecord> listElectrcityRecords(String id) {
        if (id != null) return electricityRecordRepository.findById(id);
        return electricityRecordRepository.findAll();
    }

    /**
     * Метод сохранения записи счетчика электричества
     *
     * @param building1         здание 1
     * @param building2         задние 2
     * @param school            школьно-жилое здание
     * @param electricityRecord запись счечика электричества
     * @return булевое значение успеха сохранения записи
     */
    public boolean saveElectricityRecord(String building1, String building2, String school, ElectricityRecord electricityRecord) {
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
        if (user.areElectricityRecords()) {
            if (user.getLastElectricityRecord().getElectricity() > electricityRecord.getElectricity()) {
                return false;
            }
        }
        electricityRecord.setUser(user);
        electricityRecordRepository.save(electricityRecord);
        return true;
    }

    public void deleteElectricityRecord(Long id) {
        ElectricityRecord electricityRecord = electricityRecordRepository.findById(id)
                .orElse(null);
        if (electricityRecord != null) {
            electricityRecordRepository.delete(electricityRecord);
        } else {
            log.error("Electricity Record with id = {} is not found", id);
        }
    }

    public ElectricityRecord getElectricityRecordById(Long id) {
        return electricityRecordRepository.findById(id).orElse(null);
    }

}
