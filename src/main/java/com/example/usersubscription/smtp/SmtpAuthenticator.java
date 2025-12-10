package com.example.usersubscription.smtp;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import org.springframework.stereotype.Component;

@Component
public class SmtpAuthenticator extends Authenticator {

    private final MailProperties mailProperties;

    public SmtpAuthenticator(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(
                mailProperties.getFrom(),
                System.getenv("EMAIL_PASSWORD") // password ancora via env per sicurezza
        );
    }
}