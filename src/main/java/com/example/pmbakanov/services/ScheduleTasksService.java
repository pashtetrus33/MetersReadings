package com.example.pmbakanov.services;

import com.example.pmbakanov.models.User;
import com.example.pmbakanov.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduleTasksService {
    @Autowired
    private MailSender mailSender;
    private UserRepository userRepository;

    @Scheduled(cron = "${interval-in-cron}")
    public void periodStartMailNotification() throws InterruptedException {
        mailSender.sendMail("pashtet_rus@mail.ru", "Начало периода подачи показаний счетчиков воды",
                "Добрый день, \n" + "Пожалуйста, передайте показания счетчиков воды до 24 числа текущего месяца.");
        for (User person : userRepository.findAll()) {
            mailSender.sendMail("pashtet_rus@mail.ru", "Начало периода подачи показаний счетчиков воды",
                    "Добрый день, " + person.getName() + "\n" + "Пожалуйста, передайте показания счетчиков воды до 24 числа текущего месяца.");
        }
    }

    @Scheduled(cron = "${interval-in-cron2}")
    public void periodAlmostFinishedMailNotification() throws InterruptedException {
        for (User person : userRepository.findAll()) {
            if ((!person.areRecords()) || (person.getLastRecord().getDateOfCreated().getMonth() != LocalDateTime.now().getMonth())) {
                    mailSender.sendMail("pashtet_rus@mail.ru", "Окончание периода подачи показаний счетчиков воды",
                            "Добрый день, " + person.getName() + "\n" +
                                    "Пожалуйста, передайте показания счетчиков воды.");
            }
        }
    }
}
