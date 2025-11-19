package com.example.usersubscription.repositories;

import com.example.usersubscription.entities.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

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

    @Test
    public void UserRepository_FindByEmail_ReturnsUser()
    {
        User user = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("MarioBianchi@gmail.com").password("Prova123").build();

        userRepo.save(user);

        User user2 = userRepo.findByEmail(user.getEmail());

        Assertions.assertThat(user2).isNotNull();
    }


    @Test
    public void UserRepository_UpdateUser_ReturnsUser()
    {
        User user = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("MarioBianchi@gmail.com").password("Prova123").build();

        userRepo.save(user);

        User userSaved = userRepo.findById(user.getId()).get();

        userSaved.setFirstName("Neri");
        userSaved.setPassword("123456");

        User userUpdated = userRepo.save(userSaved);

        Assertions.assertThat(userUpdated.getFirstName()).isNotNull();
        Assertions.assertThat(userUpdated.getPassword()).isNotNull();
    }

    @Test
    public void UserRepository_UserDelete_ReturnsUserIsEmpty()
    {
        User user = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("MarioBianchi@gmail.com").password("Prova123").build();

        userRepo.save(user);

        userRepo.deleteById(user.getId());
        Optional<User> userReturn = userRepo.findById(user.getId());


        Assertions.assertThat(userReturn).isEmpty();

    }

}
