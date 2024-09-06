package com.osc.userservice.service;

import com.osc.userservice.dto.UserDto;
import com.osc.userservice.response.Response;

public interface UserRegisterService {

      Response registerUser(UserDto userDTO);
}
