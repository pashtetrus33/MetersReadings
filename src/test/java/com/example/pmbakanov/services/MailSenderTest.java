package com.example.pmbakanov.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MailSenderTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private MailSender mailSenderService;

    @Test
    void sendMailTest() {
        //Arrange
        String testEmailTo = "test@mail.com";
        String testSubject = "testSubject";
        String testText = "testText";
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(testEmailTo);
        mailMessage.setSubject(testSubject);
        mailMessage.setText(testText);

        //Act
        mailSenderService.sendMail(testEmailTo, testSubject, testText);

        //Assert
        verify(mailSender, times(1)).send(mailMessage);
    }
}