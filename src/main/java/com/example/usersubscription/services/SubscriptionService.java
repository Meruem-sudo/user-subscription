package com.example.usersubscription.services;

import com.example.usersubscription.entities.Frequency;
import com.example.usersubscription.entities.Subscription;
import com.example.usersubscription.entities.User;
import com.example.usersubscription.exceptions.FrequencyException;
import com.example.usersubscription.repositories.SubscriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class SubscriptionService {


    @Autowired
    private SubscriptionRepo subscriptionRepo;


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

    public void deleteSubscription(Long id)
    {
        subscriptionRepo.deleteById(id);
    }



}
