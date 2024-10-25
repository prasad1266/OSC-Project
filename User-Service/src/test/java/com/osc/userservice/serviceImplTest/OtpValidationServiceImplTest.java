package com.osc.userservice.serviceImplTest;

import avro.OtpMessage;
import com.osc.userservice.ServiceImpl.OtpValidationServiceImpl;
import com.osc.userservice.constatnts.StatusCodes;
import com.osc.userservice.dto.ValidateOTPDto;
import com.osc.userservice.exception.EmailAlreadyExistException;
import com.osc.userservice.exception.InvalidOTPException;
import com.osc.userservice.kafka.KafkaManagerService;
import com.osc.userservice.response.Response;
import com.osc.userservice.service.OTPUpdaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OtpValidationServiceImplTest {

    @InjectMocks
    private OtpValidationServiceImpl otpValidationService;

    @Mock
    private OTPUpdaterService otpUpdaterService;

    @Mock
    private KafkaManagerService kafkaManagerService;

    @Mock
    private OtpMessage otpMessage;

@BeforeEach
void setUp(){
    MockitoAnnotations.initMocks(this);
}
    @Test
    void testOtpValidation_ValidOtp() {
        // Arrange
        ValidateOTPDto otpDto = new ValidateOTPDto();
      otpDto.setOtp("123456"); otpDto.setUserId("pras12343");

        when(kafkaManagerService.getOTP(otpDto.getUserId())).thenReturn(otpMessage);
        when(otpMessage.getOtp()).thenReturn("123456"); // Mock the OTP value

        // Act
        Response response = otpValidationService.otpValidation(otpDto);

        // Assert
        assertNotNull(response);
        assertEquals(StatusCodes.OTP_VALID, response.getCode());

    }

    @Test
    void testOtpValidation_inValidOtp() {
        // Arrange
        ValidateOTPDto otpDto = new ValidateOTPDto();
        otpDto.setOtp("123456"); otpDto.setUserId("pras12343");

        when(kafkaManagerService.getOTP(otpDto.getUserId())).thenReturn(otpMessage);
        when(otpMessage.getOtp()).thenReturn("12345"); // Mocked Wrong OTP value

        assertThrows(InvalidOTPException.class,()->otpValidationService.otpValidation(otpDto));
    }
}
