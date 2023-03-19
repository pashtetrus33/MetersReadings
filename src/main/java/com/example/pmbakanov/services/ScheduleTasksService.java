package com.example.pmbakanov.services;

import com.example.pmbakanov.models.User;
import com.example.pmbakanov.repositories.RecordRepository;
import com.example.pmbakanov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScheduleTasksService {

    private final MailSender mailSender;
    private final UserRepository userRepository;
    private final RecordRepository recordRepository;

    @Scheduled(cron = "${interval-in-cron}")
    public void periodStartMailNotification() throws InterruptedException {
        for (User person : userRepository.findAll()) {
            mailSender.sendMail(person.getEmail(), "Начало периода подачи показаний счетчиков воды",
                    "Добрый день, " + person.getName() + ".\n" + "Пожалуйста, передайте показания счетчиков воды до 24 числа текущего месяца.");
        }
    }

    @Scheduled(cron = "${interval-in-cron2}")
    public void periodAlmostFinishedMailNotification() throws InterruptedException {
        for (User person : userRepository.findAll()) {
            boolean check = person.areRecords();
            if ((!person.areRecords()) || (person.getLastRecord().getDateOfCreated().getMonth() != LocalDateTime.now().getMonth())) {
                mailSender.sendMail("pashtet_rus@mail.ru", "Окончание периода подачи показаний счетчиков воды",
                        "Добрый день, " + person.getName() + ".\n" +
                                "Пожалуйста, передайте показания счетчиков воды.");
            }
        }
    }
}
