package com.example.usersubscription.repositories;

import com.example.usersubscription.entities.Subscription;
import com.example.usersubscription.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepo extends JpaRepository <Subscription,Long> {

    List<Subscription> findByUser(User user);


}
