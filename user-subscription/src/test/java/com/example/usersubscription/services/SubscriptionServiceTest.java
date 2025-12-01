package com.example.usersubscription.services;


import com.example.usersubscription.entities.Frequency;
import com.example.usersubscription.entities.Subscription;
import com.example.usersubscription.entities.User;
import com.example.usersubscription.repositories.SubscriptionRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepo subscriptionRepo;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Test
    public void createSubscription_shouldReturnSubscription()
    {
        //Arrange
        User user = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("MarioBianchi@gmail.com").password("Prova123").build();

        Subscription subscription = Subscription.builder()
                .user(user)
                .name("Linkedin")
                .price(15.0)
                .frequency(Frequency.MONTHLY)
                .startDate(LocalDate.parse("2024-10-20"))
                .build();

        when(subscriptionRepo.save(Mockito.any(Subscription.class))).thenReturn(subscription);

        //Act
        Subscription subscription_saved = subscriptionService.addSubscription(subscription);


        //Assert
        Assertions.assertThat(subscription_saved).isNotNull();

        verify(subscriptionRepo, times(1)).save(Mockito.any(Subscription.class));

    }

    @Test
    public void shouldReturnAllSubscriptions_WhenUserHasSubscriptions() {

        //Arrange
        User user = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("MarioBianchi@gmail.com").password("Prova123").build();

        Subscription subscription = Subscription.builder()
                .user(user)
                .name("Linkedin")
                .price(15.0)
                .frequency(Frequency.MONTHLY)
                .startDate(LocalDate.parse("2024-10-20"))
                .build();

        Subscription subscription2 = Subscription.builder()
                .user(user)
                .name("Netflix")
                .price(15.0)
                .frequency(Frequency.MONTHLY)
                .startDate(LocalDate.parse("2024-10-20"))
                .build();


        List<Subscription> subscriptionList = List.of(subscription, subscription2);

        when(subscriptionRepo.findByUser(user)).thenReturn(subscriptionList);


        //Act
        List<Subscription> allSubscriptions = subscriptionService.getAllSubscriptions(user);


        //Assert
        Assertions.assertThat(allSubscriptions).isNotNull();
        Assertions.assertThat(allSubscriptions.size()).isGreaterThan(1);

    }

}
