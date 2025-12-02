package com.example.usersubscription.repositories;


import com.example.usersubscription.entities.Frequency;
import com.example.usersubscription.entities.Subscription;
import com.example.usersubscription.entities.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SubscriptionRepositoryTest {

    private UserRepo userRepo;
    private SubscriptionRepo subscriptionRepo;

    @Autowired
    public SubscriptionRepositoryTest(SubscriptionRepo subscriptionRepo, UserRepo userRepo) {
        this.subscriptionRepo = subscriptionRepo;
        this.userRepo = userRepo;
    }



    @Test
    public void shouldReturnSubscriptions_whenDataIsValid()
    {
        User user = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("MarioBianchi@gmail.com").password("Prova123").build();

        userRepo.save(user);
        Subscription subscription = Subscription.builder().user(user).name("Linkedin").price(15.0).frequency(Frequency.MONTHLY).startDate(LocalDate.parse("2024-11-20")).build();


        Subscription savedSubscription = subscriptionRepo.save(subscription);


        Assertions.assertThat(savedSubscription).isNotNull();
        Assertions.assertThat(savedSubscription.getId()).isGreaterThan(0);


    }


    @Test
    public void shouldReturnSubscriptions_whenFindAll()
    {
        User user = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("MarioBianchi@gmail.com").password("Prova123").build();

        userRepo.save(user);
        Subscription subscription = Subscription.builder().user(user).name("Linkedin").price(15.0).frequency(Frequency.MONTHLY).startDate(LocalDate.parse("2024-11-20")).build();
        Subscription subscription2 = Subscription.builder().user(user).name("Netflix").price(15.0).frequency(Frequency.MONTHLY).startDate(LocalDate.parse("2024-10-20")).build();

        subscriptionRepo.save(subscription);
        subscriptionRepo.save(subscription2);


        List<Subscription> subscriptionList = subscriptionRepo.findAll();

        Assertions.assertThat(subscriptionList).isNotNull();
        Assertions.assertThat(subscriptionList.size()).isEqualTo(2);
    }

    @Test
    public void shouldReturnSubscription_whenFindById()
    {
        User user = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("MarioBianchi@gmail.com").password("Prova123").build();

        userRepo.save(user);

        Subscription subscription = Subscription.builder().user(user).name("Linkedin").price(15.0).frequency(Frequency.MONTHLY).startDate(LocalDate.parse("2024-11-20")).build();

        Subscription subscriptionSaved = subscriptionRepo.save(subscription);

        Subscription subscriptionById = subscriptionRepo.findById(subscriptionSaved.getId()).get();



        Assertions.assertThat(subscriptionById).isNotNull();
    }

    @Test
    public void shouldUpdateSubscription_whenDataChanges()
    {
        User user = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("MarioBianchi@gmail.com").password("Prova123").build();

        userRepo.save(user);

        Subscription subscription = Subscription.builder().user(user).name("Linkedin").price(15.0).frequency(Frequency.MONTHLY).startDate(LocalDate.parse("2024-11-20")).build();

        subscriptionRepo.save(subscription);

        Subscription subscriptionById = subscriptionRepo.findById(subscription.getId()).get();

        subscription.setFrequency(Frequency.YEARLY);
        subscription.setName("Amazon");
        subscription.setPrice(150.0);

        Subscription updatedSubscription = subscriptionRepo.save(subscriptionById);


        Assertions.assertThat(updatedSubscription.getName()).isNotNull();
        Assertions.assertThat(updatedSubscription.getId()).isNotNull();
        Assertions.assertThat(updatedSubscription.getPrice()).isNotNull();
        Assertions.assertThat(updatedSubscription.getStartDate()).isNotNull();
        Assertions.assertThat(updatedSubscription.getUser()).isNotNull();
        Assertions.assertThat(updatedSubscription.getFrequency()).isNotNull();
    }

    @Test
    public void shouldDeleteSubscription_whenIdExists()
    {
        User user = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("MarioBianchi@gmail.com").password("Prova123").build();

        userRepo.save(user);

        Subscription subscription = Subscription.builder().user(user).name("Linkedin").price(15.0).frequency(Frequency.MONTHLY).startDate(LocalDate.parse("2024-11-20")).build();

        subscriptionRepo.save(subscription);

        subscriptionRepo.deleteById(subscription.getId());
        Optional<Subscription> subscriptionReturn = subscriptionRepo.findById(subscription.getId());


        Assertions.assertThat(subscriptionReturn).isEmpty();

    }

}
