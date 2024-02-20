package com.example.pmbakanov.services;

import com.example.pmbakanov.configurations.ExcelExportUtils;
import com.example.pmbakanov.models.ElectricityRecord;
import com.example.pmbakanov.models.MeterReading;
import com.example.pmbakanov.models.User;
import com.example.pmbakanov.models.enums.SpecialAdresses;
import com.example.pmbakanov.repositories.ElectricityRecordRepository;
import com.example.pmbakanov.repositories.MeterReadingRepository;
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
    private final MeterReadingRepository meterReadingRepository;
    private final ElectricityRecordRepository electricityRecordRepository;
    private final UserRepository userRepository;

    public List<MeterReading> listRecords(String id) {
        if (id != null) return meterReadingRepository.findById(id);
        return meterReadingRepository.findAll();
    }

    public void exportCustomerToExcel(HttpServletResponse response) throws IOException {
        List<MeterReading> meterReadingList = meterReadingRepository.findAll();
        List<ElectricityRecord> electricityRecordList = electricityRecordRepository.findAll();
        Collections.sort(meterReadingList);
        ExcelExportUtils exportUtils = new ExcelExportUtils(meterReadingList, electricityRecordList);
        exportUtils.exportDataToExcel(response);
    }

    public boolean saveRecord(Principal principal, MeterReading meterReading) {

        User currentUser = getUserByPrincipal(principal);
        meterReading.setUser(currentUser);

        MeterReading lastMeterReading = currentUser.getLastRecord();
        if (lastMeterReading != null) {
            if (meterReading.getKitchenHot() == null && lastMeterReading.getKitchenHot() != null) {
                meterReading.setKitchenCold(lastMeterReading.getKitchenCold());
                meterReading.setKitchenHot(lastMeterReading.getKitchenHot());
            } else if ((meterReading.getKitchenHot() == null) || (meterReading.getKitchenCold() == null)) {
                lastMeterReading.setKitchenCold(0f);
                lastMeterReading.setKitchenHot(0f);
                meterReading.setKitchenCold(0f);
                meterReading.setKitchenHot(0f);
            }

            if ((lastMeterReading.getToiletCold() > meterReading.getToiletCold()) || (lastMeterReading.getToiletHot() > meterReading.getToiletHot())) {
                return false;
            }
            if ((lastMeterReading.getKitchenCold() > meterReading.getKitchenCold()) || (lastMeterReading.getKitchenHot() > meterReading.getKitchenHot())) {
                return false;
            }
        }

        User neighborUser = null;
        if (meterReading.getNeighborCold() != null) {
            for (SpecialAdresses address : SpecialAdresses.values()) {
                if (address.getTitle().equals(currentUser.getAddress()))
                    neighborUser = userRepository.findByAddress(address.getNeighborAddress());
            }

            if (neighborUser != null) {
                MeterReading lastNeighborMeterReading;
                if (neighborUser.areRecords()) {
                    lastNeighborMeterReading = neighborUser.getLastRecord();
                    if ((lastNeighborMeterReading.getKitchenCold() != null) && (lastNeighborMeterReading.getKitchenCold() > meterReading.getNeighborCold())) {
                        return false;
                    }
                    if ((lastNeighborMeterReading.getKitchenHot() != null) && (lastNeighborMeterReading.getKitchenHot() > meterReading.getNeighborHot())) {
                        return false;
                    }
                }
                if (neighborUser.areRecords() && (neighborUser.getLastRecord().getDateOfCreated().getMonth() == LocalDateTime.now().getMonth())) {
                    lastNeighborMeterReading = neighborUser.getLastRecord();
                    lastNeighborMeterReading.setKitchenCold(meterReading.getNeighborCold());
                    lastNeighborMeterReading.setKitchenHot(meterReading.getNeighborHot());
                    meterReadingRepository.save(lastNeighborMeterReading);

                } else {
                    lastMeterReading = new MeterReading();
                    lastMeterReading.setUser(neighborUser);
                    lastMeterReading.setDateOfCreated(LocalDateTime.now());
                    lastMeterReading.setDateOfCreatedString(LocalDateTime.now().minusHours(TIME_SHIFT).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
                    lastMeterReading.setKitchenCold(meterReading.getNeighborCold());
                    lastMeterReading.setKitchenHot(meterReading.getNeighborHot());
                    lastMeterReading.setToiletCold(0f);
                    lastMeterReading.setToiletHot(0f);
                    meterReadingRepository.save(lastMeterReading);
                }
            }
        }
        for (User user : userRepository.findAll()) {
            if (user.isAdmin())
                mailSender.sendMail(user.getEmail(), "Новые показания счетчиков", meterReading.getUser().getName() + "\n" +
                        meterReading.getUser().getAddress() + "\n" +
                        "Kухня (хол.): " + meterReading.getKitchenCold() + "\n" +
                        "Kухня (гор.): " + meterReading.getKitchenHot() + "\n" +
                        "Ванная (хол.): " + meterReading.getToiletCold() + "\n" +
                        "Ванная (гор.): " + meterReading.getToiletHot() + "\n" +
                        "Сосед (кухня хол.): " + meterReading.getNeighborCold() + "\n" +
                        "Сосед (кухня гор.): " + meterReading.getNeighborHot());
        }
        if (meterReading.getKitchenCold() == null){
            meterReading.setKitchenCold(0f);
        }
        if (meterReading.getKitchenHot() == null){
            meterReading.setKitchenHot(0f);
        }
        meterReadingRepository.save(meterReading);
        return true;
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }


    public void deleteRecord(Long id) {
        MeterReading meterReading = meterReadingRepository.findById(id)
                .orElse(null);
        if (meterReading != null) {
            if (meterReading.getNeighborCold() != null) {
                User neighborUser = null;
                for (SpecialAdresses address : SpecialAdresses.values()) {
                    if (address.getTitle().equals(meterReading.getUser().getAddress()))
                        neighborUser = userRepository.findByAddress(address.getNeighborAddress());
                }
                assert neighborUser != null;
                User currentUser = meterReading.getUser();
                meterReadingRepository.delete(meterReading);
                log.info("MeterReading with id = {} was deleted", id);
                if (neighborUser.getLastRecord() != null && currentUser.getLastRecord() != null) {
                    neighborUser.getLastRecord().setKitchenCold(currentUser.getLastRecord().getNeighborCold());
                    neighborUser.getLastRecord().setKitchenHot(currentUser.getLastRecord().getNeighborHot());
                }

            } else {
                meterReadingRepository.delete(meterReading);
                log.info("MeterReading with id = {} was deleted", id);
            }

        } else {
            log.error("MeterReading with id = {} is not found", id);
        }
    }

    public MeterReading getRecordById(Long id) {
        return meterReadingRepository.findById(id).orElse(null);
    }


}
