package com.osc.userservice.serviceImplTest;
import com.osc.userservice.constatnts.StatusCodes;
import com.osc.userservice.response.Response;
import com.osc.userservice.service.verifyUniqueEmailService;
import com.osc.userservice.ServiceImpl.EmailVerificationServiceImpl;
import com.osc.userservice.kafka.KafkaManagerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailVerificationServiceImplTest {

    @InjectMocks
    private EmailVerificationServiceImpl emailVerificationService;

    @Mock
    private verifyUniqueEmailService verifyUniqueEmailService;

    @Mock
    private KafkaManagerService kafkaManagerService;
    @Test
    void testIsEmailExist_EmailExists() {
        // Arrange
        String email = "prasadh1266@gmail.com";
        String otp = "123456"; // Example OTP
        when(verifyUniqueEmailService.verifyUniqueEmail(email)).thenReturn(true);

        // Act
        Response response = emailVerificationService.isEmailExist(email);

        // Assert
        assertNotNull(response);
        assertEquals(response.getCode(), StatusCodes.VALID_EMAIL);
    }

}
