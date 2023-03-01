package com.example.pmbakanov.services;

import com.example.pmbakanov.models.Record;
import com.example.pmbakanov.models.User;
import com.example.pmbakanov.repositories.RecordRepository;
import com.example.pmbakanov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecordService {
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
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByLogin(principal.getName());
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
