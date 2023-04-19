package com.example.pmbakanov.services;

import com.example.pmbakanov.models.User;
import com.example.pmbakanov.repositories.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

import static com.example.pmbakanov.services.UserService.DEPLOY_WEBSITE;

@Service
@RequiredArgsConstructor
@Slf4j
@Setter
@Getter
public class ScheduleTasksService {

    private final MailSender mailSender;
    private final UserRepository userRepository;

    @Scheduled(cron = "${interval-in-cron}")
    public void periodStartMailNotification() {
        for (User person : userRepository.findAll()) {
            mailSender.sendMail(person.getEmail(), "Начало периода подачи показаний счетчиков воды",
                    "Добрый день, " + person.getName() + ".\n" + "Пожалуйста, передайте показания счетчиков воды до 24 числа текущего месяца."
                            + ".\n" + DEPLOY_WEBSITE);
        }
    }

    @Scheduled(cron = "${interval-in-cron2}")
    @Transactional
    public void periodAlmostFinishedMailNotification() {
        for (User person : userRepository.findAll()) {
            if ((!person.areRecords()) || (person.getLastRecord().getDateOfCreated().getMonth() != LocalDateTime.now().getMonth())) {
                mailSender.sendMail(person.getEmail(), "Окончание периода подачи показаний счетчиков воды",
                        "Добрый день, " + person.getName() + ".\n" +
                                "Пожалуйста, передайте показания счетчиков воды." + ".\n" + DEPLOY_WEBSITE);
            }
        }
    }

    @Value("${pingtask.url}")
    private String url;

    @Scheduled(fixedRateString = "${pingtask.period}")
    public void pingMe() {
        try {
            URL url = new URL(getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            log.info("Ping {}, OK: response code {}", url.getHost(), connection.getResponseCode());
            connection.disconnect();
        } catch (IOException e) {
            log.error("Ping FAILED");
            e.printStackTrace();
        }
    }
}
