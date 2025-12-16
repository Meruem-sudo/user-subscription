package com.example.usersubscription.startup;

import com.example.usersubscription.services.SubscriptionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final SubscriptionService subscriptionService;

    public StartupRunner(SubscriptionService subscriptionService)
    {
        this.subscriptionService = subscriptionService;
    }


    @Override
    public void run(String... args) throws Exception {

        subscriptionService.checkExpiredSubscriptions();
        subscriptionService.updateEndDateExpiredSubscriptions();

    }
}
