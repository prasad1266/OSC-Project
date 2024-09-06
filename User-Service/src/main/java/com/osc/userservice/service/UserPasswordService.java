package com.osc.userservice.service;

import com.osc.userservice.dto.PasswordDto;
import com.osc.userservice.dto.UpdatePasswordDto;
import com.osc.userservice.response.Response;

public interface UserPasswordService {

      Response createPassword(PasswordDto passwordDto) ;
      Response updatePassword(UpdatePasswordDto updatePasswordDto);
}
