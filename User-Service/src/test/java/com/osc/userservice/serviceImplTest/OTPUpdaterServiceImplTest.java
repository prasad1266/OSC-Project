package com.osc.userservice.serviceImplTest;

import avro.OtpMessage;
import com.osc.userservice.ServiceImpl.OTPUpdaterServiceImpl;
import com.osc.userservice.kafka.KafkaManagerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class OTPUpdaterServiceImplTest {

    @InjectMocks
    private OTPUpdaterServiceImpl otpUpdaterService;

    @Mock
    private KafkaManagerService kafkaManagerService;

    @Mock
    private OtpMessage otpMessage;

    @Test
    void testUpdateFailedOtpAttempts_WithValidOtpData() {
        OtpMessage otp = OtpMessage.newBuilder().setOtp("123456").setFailedAttempts(1)
                .setEmail("prasad@gmail.com")
                .setPurpose("REGISTRATION").build();

        // Arrange
        String userId = "pra1243";

        // Mock the method call on the mock OtpMessage
        when(otpMessage.getFailedAttempts()).thenReturn(1); // Mock failed attempts

        // Act
        otpUpdaterService.updateFailedOtpAttempts(userId, otpMessage);

        // Assert
//        verify(kafkaManagerService).updateFailedAttemptsInOTPTopic(eq(userId), any(OtpMessage.class));
    }

    @Test
    void testUpdateFailedOtpAttempts_WithNullOtpData() {
        // Arrange
        String userId = "Pras1423";

        // Act
        otpUpdaterService.updateFailedOtpAttempts(userId, null);

        // Assert
        verify(kafkaManagerService, never()).updateFailedAttemptsInOTPTopic(anyString(), any(OtpMessage.class));
    }
}