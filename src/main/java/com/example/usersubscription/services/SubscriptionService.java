package com.example.usersubscription.services;

import com.example.usersubscription.entities.Frequency;
import com.example.usersubscription.entities.Subscription;
import com.example.usersubscription.entities.User;
import com.example.usersubscription.exceptions.FrequencyException;
import com.example.usersubscription.repositories.SubscriptionRepo;
import com.example.usersubscription.smtp.MailProperties;
import com.example.usersubscription.smtp.PropertiesSmtp;
import com.example.usersubscription.smtp.SmtpAuthenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class SubscriptionService {


    private final PropertiesSmtp propertiesSmtp;
    private final SmtpAuthenticator authenticator;
    private final MailProperties mailProperties;

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    public SubscriptionService(PropertiesSmtp propertiesSmtp,
                               SmtpAuthenticator authenticator,
                               MailProperties mailProperties) {
        this.propertiesSmtp = propertiesSmtp;
        this.authenticator = authenticator;
        this.mailProperties = mailProperties;
    }

    public Subscription addSubscription(Subscription subscription)
    {
        switch (subscription.getFrequency())
        {
            case WEEKLY -> subscription.setEndDate(subscription.getStartDate().plusWeeks(1));
            case MONTHLY -> subscription.setEndDate(subscription.getStartDate().plusMonths(1));
            case YEARLY -> subscription.setEndDate(subscription.getStartDate().plusYears(1));
            default -> throw new FrequencyException("Frequenza non rilevata");
        }
        return subscriptionRepo.save(subscription);
    }

    public List<Subscription> getAllSubscriptions(User u)
    {
        return subscriptionRepo.findByUser(u);
    }


    public Subscription getSingleSubscription(Long id)
    {
         return subscriptionRepo.findById(id).orElseThrow(() -> new RuntimeException("Subscription non trovata"));

    }

    public void deleteSubscription(Long id)
    {
        subscriptionRepo.deleteById(id);
    }

    public Subscription updateSubscription(Subscription subscription)
    {
        return subscriptionRepo.save(subscription);
    }

    @Transactional
    public void checkExpiredSubscriptions() throws MessagingException {


        LocalDate now = LocalDate.now();
        System.out.println(subscriptionRepo.findByEndDateBefore(now));

        Session session = Session.getInstance(propertiesSmtp.getProperties(), authenticator);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailProperties.getFrom()));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(mailProperties.getTo()));
        message.setSubject("Test");
        message.setText("Corpo del messaggio");

        Transport.send(message);
    }

}
