package com.osc.userdataservice.serviceImplTest;

import com.osc.userdataservice.entity.User;
import com.osc.userdataservice.repository.UserRepository;
import com.osc.userdataservice.service.VerifyEmailAddressService;
import com.osc.userdataservice.serviceImpl.verifyEmailAddressServiceImpl;
import com.user.UniqueEmailRequest;
import com.user.UniqueEmailResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class VerifyEmailAddressServiceImplTest {
    @InjectMocks
    private VerifyEmailAddressService verifyEmailAddressService;
    @Mock
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        verifyEmailAddressService = new verifyEmailAddressServiceImpl(userRepository);
    }

    @Test
    void testEmailIsNotUnique() {
        // Arrange
        String email = "prasad@gmail.com";
        UniqueEmailRequest request = UniqueEmailRequest.newBuilder().setEmail(email).build();

        when(userRepository.findByEmail(email)).thenReturn(null);
        // Act
        UniqueEmailResponse response = verifyEmailAddressService.verifyEmailAddressIsUnique(request);
       boolean val = response.getIsUnique();
        // Assert
        Assertions.assertEquals(false,response.getIsUnique());

    }

    @Test
    void testEmailIsUnique() {
        // Arrange
        String email = "prasadh1266@gmail.com";
        UniqueEmailRequest request =  UniqueEmailRequest.newBuilder().setEmail(email).build();
        User existingUser = new User(); // Simulate an existing user
        existingUser.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(existingUser);

        // Act
        UniqueEmailResponse response = verifyEmailAddressService.verifyEmailAddressIsUnique(request);

        // Assert
        Assertions.assertEquals(true,response.getIsUnique());
        verify(userRepository).findByEmail(email);
    }
}
