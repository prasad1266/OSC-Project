package com.osc.userservice.serviceImplTest;

import com.osc.userservice.ServiceImpl.UserRegisterServiceImpl;
import com.osc.userservice.constatnts.StatusCodes;
import com.osc.userservice.dto.UserDto;
import com.osc.userservice.exception.EmailAlreadyExistException;
import com.osc.userservice.kafka.KafkaManagerService;
import com.osc.userservice.response.Response;
import com.osc.userservice.service.verifyUniqueEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRegisterServiceImplTest {

    @InjectMocks
    private UserRegisterServiceImpl userRegisterService;

    @Mock
    private verifyUniqueEmailService verifyUniqueEmailService;

    @Mock
    private KafkaManagerService kafkaManagerService;

    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        userDto = new UserDto();
        userDto.setEmail("prasadh1266@gmail.com");
        userDto.setName("Pras1423");
    }

    @Test
    public void testRegisterUser_Success() {
        // Arrange
        when(verifyUniqueEmailService.verifyUniqueEmail(userDto.getEmail())).thenReturn(false);

        // Act
        Response response = userRegisterService.registerUser(userDto);

        // Assert
        assertEquals(StatusCodes.REGISTRATION_SUCCESS, response.getCode());

    }

    @Test
    public void testRegisterUser_EmailAlreadyExists() {
        // Arrange
        when(verifyUniqueEmailService.verifyUniqueEmail(userDto.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(EmailAlreadyExistException.class, () -> userRegisterService.registerUser(userDto));
    }
}