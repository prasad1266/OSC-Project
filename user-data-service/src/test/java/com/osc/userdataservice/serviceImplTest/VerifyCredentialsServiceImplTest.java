package com.osc.userdataservice.serviceImplTest;

import com.osc.userdataservice.entity.User;
import com.osc.userdataservice.repository.UserRepository;
import com.osc.userdataservice.service.VerifyUserCredentialsService;
import com.osc.userdataservice.serviceImpl.VerrifyCredentialsServiceImpl;
import com.session.VerifyCredentialsRequest;
import com.session.VerifyCredentialsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class VerifyCredentialsServiceImplTest {
    @InjectMocks
    private VerifyUserCredentialsService verifyUserCredentialsService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        verifyUserCredentialsService = new VerrifyCredentialsServiceImpl(userRepository);
    }

    @Test
    void testVerifyCredentials_UserExists() {
        // Arrange
        String userId = "12345";
        VerifyCredentialsRequest request = VerifyCredentialsRequest.newBuilder()
                .setUserId(userId).build();

        User user = new User();
        user.setName("Prasad");
        user.setEmail("prasad@gmail.com");
        user.setPassword("Prasad@!23");
        user.setUserId("12345");
        user.setContactNumber("12568596");
        user.setDateOfBirth(LocalDate.of(1998,5,13));

        when(userRepository.findUserByUserId(userId)).thenReturn(user);
        // Act
        VerifyCredentialsResponse response = verifyUserCredentialsService.verifyCredentialsFromDB(request);

        // Assert
        Assertions.assertEquals("Prasad", response.getName());
        verify(userRepository).findUserByUserId(userId);
    }

}
