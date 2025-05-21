package com.example.demo.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // true = HTML

        mailSender.send(message);
    }
    public void sendResetPasswordEmail(String to, String token) throws MessagingException {
        String resetUrl = "http://localhost:8081/api/password/reset?token=" + token;

        String body = "<p>Clique sur le lien suivant pour réinitialiser ton mot de passe :</p>"
                + "<a href=\"" + resetUrl + "\">Réinitialiser le mot de passe</a>";

        sendEmail(to, "Réinitialisation du mot de passe", body);
    }



}

