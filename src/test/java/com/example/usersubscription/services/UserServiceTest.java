package com.example.usersubscription.services;

import com.example.usersubscription.entities.User;
import com.example.usersubscription.repositories.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    public void createUser_shoudlReturnsUser()
    {
        User user = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("MarioBianchi@gmail.com").password("Prova123").build();

        when(userRepo.save(Mockito.any(User.class))).thenReturn(user);


        User userSaved = userService.register(user);

        Assertions.assertThat(userSaved).isNotNull();


    }
    @Test
    public void shouldEncodePassword_andReturnUser_whenRegistrationIsValid() {
        // Arrange
        User user = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("MarioBianchi@gmail.com")
                .password("Prova123")
                .build();


        User savedUser = User.builder()
                .firstName("Mario")
                .lastName("Bianchi")
                .email("MarioBianchi@gmail.com")
                .password("encryptedPassword123")
                .build();

        when(userRepo.save(Mockito.any(User.class))).thenReturn(savedUser);

        // Act
        User result = userService.register(user);

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getPassword())
                .isEqualTo("encryptedPassword123");

        // Verifica che il service abbia chiamato la repository
        verify(userRepo, times(1)).save(Mockito.any(User.class));
    }



}
