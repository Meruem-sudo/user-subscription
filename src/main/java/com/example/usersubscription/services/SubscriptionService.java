package com.example.usersubscription.services;

import com.example.usersubscription.entities.Subscription;
import com.example.usersubscription.entities.User;
import com.example.usersubscription.exceptions.FrequencyException;
import com.example.usersubscription.exceptions.ValidationException;
import com.example.usersubscription.repositories.SubscriptionRepo;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

@Service
public class SubscriptionService {


    @Autowired
    private SubscriptionRepo subscriptionRepo;



    public void validateStartDate(Subscription subscription) {
        LocalDate today = LocalDate.now();
        LocalDate minDate;

        switch (subscription.getFrequency()) {
            case WEEKLY -> minDate = today.minusWeeks(1);
            case MONTHLY -> minDate = today.minusMonths(1);
            case YEARLY -> minDate = today.minusYears(1);
            default -> throw new IllegalArgumentException("Frequenza non valida");
        }

        if (subscription.getStartDate().isAfter(today)) {
            throw new ValidationException("La data di inizio non può essere futura");
        }

        if (subscription.getStartDate().isBefore(minDate)) {
            throw new ValidationException(
                    "La data di inizio non è valida per la frequenza selezionata"
            );
        }
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


    @Transactional(readOnly = true)
    public List<Subscription> getExpiringSubscriptions() {
        return subscriptionRepo.findByEndDate(LocalDate.now().plusDays(1));
    }


    public List<Subscription> getExpiredSubscriptions() {
        return subscriptionRepo.findByEndDate(LocalDate.now());
    }



    public void sendExpirationEmails(List<Subscription> subscriptions) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        String to = System.getenv("EMAIL_TO");
        String from = System.getenv("EMAIL_FROM");
        final String username = System.getenv("EMAIL_FROM");
        final String password = System.getenv("EMAIL_PASSWORD");
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth",true);
        props.put("mail.smtp.starttls.enable",true);
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.port","587");

        Authenticator authenticator = new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        };
        Session session = Session.getInstance(props, authenticator);

        for (int i = 0; i<subscriptions.size();i++) {

            Subscription tempSubscription = subscriptions.get(i);
            String formattedDate = tempSubscription.getEndDate().format(formatter);
            String subject = "Scadenza abbonamento a " + tempSubscription.getName();
            String content = "Abbonamento a " + tempSubscription.getName() + " in scadenza il giorno " + formattedDate
                    + " al costo di " + tempSubscription.getPrice() + "€";

            try {

                Message message = new MimeMessage(session);

                message.setFrom(new InternetAddress(from));

                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

                message.setSubject(subject);

                message.setContent(content, "text/plain; charset=UTF-8");

                Transport.send(message);
                System.out.println("Email inviata correttamente");
            } catch (MessagingException e) {
                System.out.println(e);
            }
        }



    }
    public void checkExpiredSubscriptions() throws MessagingException {
        List<Subscription> subscriptions = getExpiringSubscriptions();
        sendExpirationEmails(subscriptions);
    }

    @Transactional
    public void updateEndDateExpiredSubscriptions() {
        List<Subscription> expiredSubscriptions = getExpiredSubscriptions();

        for (Subscription subscription : expiredSubscriptions) {
            subscription.setEndDate(switch (subscription.getFrequency()) {
                case WEEKLY -> subscription.getEndDate().plusWeeks(1);
                case MONTHLY -> subscription.getEndDate().plusMonths(1);
                case YEARLY -> subscription.getEndDate().plusYears(1);
            });
        }
    }

}
