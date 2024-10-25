package com.osc.userdataservice.serviceImplTest;

import com.osc.userdataservice.entity.User;
import com.osc.userdataservice.repository.UserRepository;
import com.osc.userdataservice.serviceImpl.RegisterUserServiceImpl;
import com.user.RegisterUserRequest;
import com.user.RegisterUserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RegisterUserServiceImplTest {
    @InjectMocks
    private RegisterUserServiceImpl registerUserServiceimpl;
    @Mock
    private UserRepository userRepository;

    @Test
    void registerUserSuccess() {

        RegisterUserRequest request = RegisterUserRequest.newBuilder().setUserId("pra1234").setName("Prasad")
                .setDob("1998-05-13").setMobileNumber("9876542310").setEmail("prasad@gmail.com").setPassword("Prasad@123")
                .build();

        User mockUser = new User();
        mockUser.setUserId("pra1234"); // Assume user ID is 1
        mockUser.setDateOfBirth(LocalDate.of(1998,5,13));
        //when(userRepository.save(mockUser)).thenReturn(mockUser);


        RegisterUserResponse response = registerUserServiceimpl.registerUser(request);


        assertNotNull(response);
        assertTrue(response.getSuccess());
        }

}
