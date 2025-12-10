package com.example.usersubscription.smtp;

import com.example.usersubscription.smtp.MailProperties;
import org.springframework.stereotype.Component;
import java.util.Properties;

@Component
public class PropertiesSmtp {

    private final Properties properties = new Properties();

    public PropertiesSmtp(MailProperties mailProperties) {
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", mailProperties.getHost());
        properties.put("mail.smtp.port", String.valueOf(mailProperties.getPort()));
    }

    public Properties getProperties() {
        return properties;
    }
}