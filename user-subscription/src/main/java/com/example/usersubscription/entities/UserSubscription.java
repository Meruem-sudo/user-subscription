package com.example.usersubscription.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class UserSubscription {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long user_id;
    private Long subscription_id;

}
