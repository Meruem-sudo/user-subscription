package com.example.usersubscription.repositories;

import com.example.usersubscription.entities.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepo userRepo;

    @Test
    public void UserRepository_SaveAll_ReturnSavedUser()
    {

        //Arrange
        User user = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("MarioBianchi@gmail.com").password("Prova123").build();

        //Act
        User savedUser = userRepo.save(user);


        //Assert

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void UserRepository_GetAll_ReturnMoreThanOneUser()
    {
        User user = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("MarioBianchi@gmail.com").password("Prova123").build();

        User user2 = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("BianchiMario@gmail.com").password("Prova123").build();


        userRepo.save(user);
        userRepo.save(user2);


        List<User> userList = userRepo.findAll();

        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);

    }

    @Test
    public void UserRepository_FindById_ReturnsUser()
    {
        User user = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("MarioBianchi@gmail.com").password("Prova123").build();

        userRepo.save(user);

        User user2 = userRepo.findById(user.getId()).get();

        Assertions.assertThat(user2).isNotNull();

    }

}
