package com.example.pmbakanov.services;

import com.example.pmbakanov.models.User;
import com.example.pmbakanov.repositories.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTasksService {
    private MailSender mailSender;
    private UserRepository userRepository;

    @Scheduled(cron = "${interval-in-cron}")
    public void periodStartMailNotification() throws InterruptedException {
//        for (User person: userRepository.findAll()) {
//            mailSender.sendMail(person.getEmail(), "Начало периода подачи показаний счетчиков воды",
//                    "Добрый день," + person.getName() + "\n" + "Пожалуйста, передайте показания счетчиков воды до 24 числа текущего месяца.");
//        }
        mailSender.sendMail("pashtet_rus@mail.ru", "Начало периода подачи показаний счетчиков воды",
                "Добрый день, пожалуйста, передайте показания счетчиков воды до 24 числа текущего месяца.");
    }
}
