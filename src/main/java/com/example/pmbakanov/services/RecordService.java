package com.example.pmbakanov.services;

import com.example.pmbakanov.configurations.ExcelExportUtils;
import com.example.pmbakanov.models.ElectricityRecord;
import com.example.pmbakanov.models.Record;
import com.example.pmbakanov.models.User;
import com.example.pmbakanov.models.enums.SpecialAdresses;
import com.example.pmbakanov.repositories.ElectricityRecordRepository;
import com.example.pmbakanov.repositories.RecordRepository;
import com.example.pmbakanov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static com.example.pmbakanov.controllers.UserController.TIME_SHIFT;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecordService {
    private final MailSender mailSender;
    private final RecordRepository recordRepository;
    private final ElectricityRecordRepository electricityRecordRepository;
    private final UserRepository userRepository;

    public List<Record> listRecords(String id) {
        if (id != null) return recordRepository.findById(id);
        return recordRepository.findAll();
    }

    public void exportCustomerToExcel(HttpServletResponse response) throws IOException {
        List<Record> recordList = recordRepository.findAll();
        List<ElectricityRecord> electricityRecordList = electricityRecordRepository.findAll();
        Collections.sort(recordList);
        ExcelExportUtils exportUtils = new ExcelExportUtils(recordList, electricityRecordList);
        exportUtils.exportDataToExcel(response);
    }

    public boolean saveRecord(Principal principal, Record record) {

        User currentUser = getUserByPrincipal(principal);
        record.setUser(currentUser);

        Record lastRecord = currentUser.getLastRecord();
        if (lastRecord != null) {
            if (record.getKitchenHot() == null && lastRecord.getKitchenHot() != null) {
                record.setKitchenCold(lastRecord.getKitchenCold());
                record.setKitchenHot(lastRecord.getKitchenHot());
            } else if ((record.getKitchenHot() == null) || (record.getKitchenCold() == null)) {
                lastRecord.setKitchenCold(0f);
                lastRecord.setKitchenHot(0f);
                record.setKitchenCold(0f);
                record.setKitchenHot(0f);
            }

            if ((lastRecord.getToiletCold() > record.getToiletCold()) || (lastRecord.getToiletHot() > record.getToiletHot())) {
                return false;
            }
            if ((lastRecord.getKitchenCold() > record.getKitchenCold()) || (lastRecord.getKitchenHot() > record.getKitchenHot())) {
                return false;
            }
        }

        User neighborUser = null;
        if (record.getNeighborCold() != null) {
            for (SpecialAdresses address : SpecialAdresses.values()) {
                if (address.getTitle().equals(currentUser.getAddress()))
                    neighborUser = userRepository.findByAddress(address.getNeighborAddress());
            }

            if (neighborUser != null) {
                Record lastNeighborRecord;
                if (neighborUser.areRecords()) {
                    lastNeighborRecord = neighborUser.getLastRecord();
                    if ((lastNeighborRecord.getKitchenCold() != null) && (lastNeighborRecord.getKitchenCold() > record.getNeighborCold())) {
                        return false;
                    }
                    if ((lastNeighborRecord.getKitchenHot() != null) && (lastNeighborRecord.getKitchenHot() > record.getNeighborHot())) {
                        return false;
                    }
                }
                if (neighborUser.areRecords() && (neighborUser.getLastRecord().getDateOfCreated().getMonth() == LocalDateTime.now().getMonth())) {
                    lastNeighborRecord = neighborUser.getLastRecord();
                    lastNeighborRecord.setKitchenCold(record.getNeighborCold());
                    lastNeighborRecord.setKitchenHot(record.getNeighborHot());
                    recordRepository.save(lastNeighborRecord);

                } else {
                    lastRecord = new Record();
                    lastRecord.setUser(neighborUser);
                    lastRecord.setDateOfCreated(LocalDateTime.now());
                    lastRecord.setDateOfCreatedString(LocalDateTime.now().minusHours(TIME_SHIFT).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
                    lastRecord.setKitchenCold(record.getNeighborCold());
                    lastRecord.setKitchenHot(record.getNeighborHot());
                    lastRecord.setToiletCold(0f);
                    lastRecord.setToiletHot(0f);
                    recordRepository.save(lastRecord);
                }
            }
        }
        for (User user : userRepository.findAll()) {
            if (user.isAdmin())
                mailSender.sendMail(user.getEmail(), "Новые показания счетчиков", record.getUser().getName() + "\n" +
                        record.getUser().getAddress() + "\n" +
                        "Kухня (хол.): " + record.getKitchenCold() + "\n" +
                        "Kухня (гор.): " + record.getKitchenHot() + "\n" +
                        "Ванная (хол.): " + record.getToiletCold() + "\n" +
                        "Ванная (гор.): " + record.getToiletHot() + "\n" +
                        "Сосед (кухня хол.): " + record.getNeighborCold() + "\n" +
                        "Сосед (кухня гор.): " + record.getNeighborHot());
        }
        if (record.getKitchenCold() == null){
            record.setKitchenCold(0f);
        }
        if (record.getKitchenHot() == null){
            record.setKitchenHot(0f);
        }
        recordRepository.save(record);
        return true;
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }


    public void deleteRecord(Long id) {
        Record record = recordRepository.findById(id)
                .orElse(null);
        if (record != null) {
            if (record.getNeighborCold() != null) {
                User neighborUser = null;
                for (SpecialAdresses address : SpecialAdresses.values()) {
                    if (address.getTitle().equals(record.getUser().getAddress()))
                        neighborUser = userRepository.findByAddress(address.getNeighborAddress());
                }
                assert neighborUser != null;
                User currentUser = record.getUser();
                recordRepository.delete(record);
                log.info("Record with id = {} was deleted", id);
                if (neighborUser.getLastRecord() != null && currentUser.getLastRecord() != null) {
                    neighborUser.getLastRecord().setKitchenCold(currentUser.getLastRecord().getNeighborCold());
                    neighborUser.getLastRecord().setKitchenHot(currentUser.getLastRecord().getNeighborHot());
                }

            } else {
                recordRepository.delete(record);
                log.info("Record with id = {} was deleted", id);
            }

        } else {
            log.error("Record with id = {} is not found", id);
        }
    }

    public Record getRecordById(Long id) {
        return recordRepository.findById(id).orElse(null);
    }


}
