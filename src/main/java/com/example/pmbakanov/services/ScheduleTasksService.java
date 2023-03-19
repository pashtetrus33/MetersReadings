package com.example.pmbakanov.services;

import com.example.pmbakanov.models.Record;
import com.example.pmbakanov.models.User;
import com.example.pmbakanov.repositories.RecordRepository;
import com.example.pmbakanov.repositories.RequestRepository;
import com.example.pmbakanov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleTasksService {

    private final MailSender mailSender;
    private final UserRepository userRepository;

    @Scheduled(cron = "${interval-in-cron}")
    public void periodStartMailNotification() throws InterruptedException {
        for (User person : userRepository.findAll()) {
            mailSender.sendMail(person.getEmail(), "Начало периода подачи показаний счетчиков воды",
                    "Добрый день, " + person.getName() + ".\n" + "Пожалуйста, передайте показания счетчиков воды до 24 числа текущего месяца.");
        }
    }

    @Scheduled(cron = "${interval-in-cron2}")
    @Transactional
    public void periodAlmostFinishedMailNotification() throws InterruptedException {
        for (User person : userRepository.findAll()) {
            if ((!person.areRecords()) || (person.getLastRecord().getDateOfCreated().getMonth() != LocalDateTime.now().getMonth())) {
                mailSender.sendMail(person.getEmail(), "Окончание периода подачи показаний счетчиков воды",
                        "Добрый день, " + person.getName() + ".\n" +
                                "Пожалуйста, передайте показания счетчиков воды.");
            }
        }
    }
}
