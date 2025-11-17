package com.example.usersubscription.services;

import com.example.usersubscription.entities.Subscription;
import com.example.usersubscription.entities.User;
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
        return subscriptionRepo.save(subscription);
    }

    public List<Subscription> getAllSubscriptions(User u)
    {
        return subscriptionRepo.findByUser(u);
    }

}
