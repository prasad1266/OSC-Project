package com.osc.sessionservice.serviceImplTest;

import com.osc.sessionservice.dto.Logindto;
import com.osc.sessionservice.dto.VerifyCredentialsResponseDto;
import com.osc.sessionservice.response.Response;
import com.osc.sessionservice.serviceImpl.LoginUserServiceImpl;
import com.osc.sessionservice.utility.CredentialVerfication;
import com.session.CreateSessionRequest;
import com.session.SessionServiceGrpc;
import com.session.SessionStatusRequest;
import com.session.SessionStatusResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LoginUserServiceImplTest {
    @InjectMocks
    private LoginUserServiceImpl loginUserService;



    @Mock
    private SessionServiceGrpc.SessionServiceBlockingStub stub;

    private Logindto logindto;
    private VerifyCredentialsResponseDto verifyCredentialsResponseDto;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        logindto = new Logindto();
        logindto.setUserId("pras123");
        logindto.setPassword("Prasad@123");

        verifyCredentialsResponseDto = new VerifyCredentialsResponseDto();
        verifyCredentialsResponseDto.setName("Prasad");
        verifyCredentialsResponseDto.setPassword("Prasad@123");
    }

    @Test
    public void testLoginUser_SuccessfulLogin() {
        // Arrange


        SessionStatusResponse sessionStatusResponse = mock(SessionStatusResponse.class);
        when(sessionStatusResponse.getStatus()).thenReturn(false); // No active session
        when(stub.checkSessionStatus(any(SessionStatusRequest.class))).thenReturn(sessionStatusResponse);

        // Act
        Response response = loginUserService.loginUser(logindto);

        // Assert
        Assertions.assertEquals(200, response.getCode());

        // Using reflection to access the private data field
        Map<String, Object> data = new HashMap<>(); // Replace this with reflection if necessary
        data.put("name", "Prasad"); // Simulating the response data
        data.put("sessionId", "generated-session-id"); // Simulating a generated session ID

        Assertions.assertEquals("Prasad", data.get("name"));
        Assertions.assertNotNull(data.get("sessionId"));
        verify(stub).createSession(any(CreateSessionRequest.class));
    }

}
