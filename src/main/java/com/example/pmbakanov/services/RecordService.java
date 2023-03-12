package com.example.pmbakanov.services;

import com.example.pmbakanov.models.Record;
import com.example.pmbakanov.models.User;
import com.example.pmbakanov.models.enums.SpecialAdresses;
import com.example.pmbakanov.repositories.RecordRepository;
import com.example.pmbakanov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecordService {
    @Autowired
    private MailSender mailSender;
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;

    public List<Record> listRecords(String id) {
        if (id != null) return recordRepository.findById(id);
        return recordRepository.findAll();
    }

    public void saveRecord(Principal principal, Record record) throws IOException {
        record.setUser(getUserByPrincipal(principal));

        log.info("Saving new Record.");
        recordRepository.save(record);
        User neighborUser = null;
        if (record.getNeighborCold() != null) {
            for (SpecialAdresses address : SpecialAdresses.values()) {
                if (address.getTitle().equals(record.getUser().getAddress()))
                    neighborUser = userRepository.findByAddress(address.getNeighborAddress());
            }
            Record lastRecord = neighborUser.getLastRecord();
            if (neighborUser.areRecords() && (lastRecord.getDateOfCreated().getMonth() == LocalDateTime.now().getMonth())) {
                lastRecord.setKitchenCold(record.getNeighborCold());
                lastRecord.setKitchenHot(record.getNeighborHot());
                recordRepository.save(lastRecord);
            } else {
                lastRecord = new Record();
                lastRecord.setUser(neighborUser);
                lastRecord.setDateOfCreated(LocalDateTime.now());
                lastRecord.setKitchenCold(record.getNeighborCold());
                lastRecord.setKitchenHot(record.getNeighborHot());
                lastRecord.setToiletCold(0);
                lastRecord.setToiletHot(0);
                recordRepository.save(lastRecord);
            }
        }
        for (User user : userRepository.findAll()) {
            if (user.isAdmin())
                mailSender.sendMail(user.getEmail(), "Новые показания счетчиков", record.getUser().getName() + "\n" +
                        record.getUser().getAddress() + "\n" +
                        "Kухня (хол.): " + record.getKitchenCold() + "\n" +
                        "Kухня (гор.): " + +record.getKitchenHot() + "\n" +
                        "Ванная (хол.): " + record.getToiletCold() + "\n" +
                        "Ванная (гор.): " + record.getToiletHot() + "\n" +
                        "Сосед (кухня хол.): " + record.getNeighborCold() + "\n" +
                        "Сосед (кухня гор.): " + record.getNeighborHot());
        }
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }


    public void deleteRecord(Long id) {
        Record record = recordRepository.findById(id)
                .orElse(null);
        if (record != null) {
            recordRepository.delete(record);
            log.info("Record with id = {} was deleted", id);

        } else {
            log.error("Record with id = {} is not found", id);
        }
    }

    public Record getRecordById(Long id) {
        return recordRepository.findById(id).orElse(null);
    }
}
