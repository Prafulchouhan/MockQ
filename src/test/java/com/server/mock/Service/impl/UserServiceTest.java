package com.server.mock.Service.impl;
import com.server.mock.Service.UserService;
import com.server.mock.dto.RegistrationBody;
import com.server.mock.exception.UserAlreadyExistsException;
import com.server.mock.model.User;
import com.server.mock.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EncryptionService encryptionService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testRegisterUser_ThrowsUserAlreadyExistsException() {
        // Mocking the behavior of UserRepository.findByUserNameIgnoreCase to return a non-empty Optional
        when(userRepository.findByUserNameIgnoreCase(any(String.class))).thenReturn(Optional.of(new User()));
        // Creating a RegistrationBody with a userName that already exists
        RegistrationBody registrationBody = new RegistrationBody();
        registrationBody.setUserName("existingUserName");

        // Asserting that UserAlreadyExistsException is thrown
        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(registrationBody));

        // Verifying that userRepository.findByUserNameIgnoreCase is called once with the expected argument
        verify(userRepository, times(1)).findByUserNameIgnoreCase("existingUserName");
        // Verifying that userRepository.findByEmailIgnoreCase is never called
        verify(userRepository, never()).findByEmailIgnoreCase(any(String.class));
        // Verifying that userRepository.save is never called
        verify(userRepository, never()).save(any(User.class));
        // Verifying that encryptionService.encryptPassword is never called
        verify(encryptionService, never()).encryptPassword(any(String.class));
    }

    // Add more test cases to cover different scenarios such as successful registration, etc.


    @Test
    void logIn() {
    }

    @Test
    void getName() {
    }

    @Test
    void findAllUsers() {
    }

    @Test
    void findById() {
    }
}
