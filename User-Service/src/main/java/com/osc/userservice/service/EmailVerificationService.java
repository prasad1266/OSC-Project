package com.osc.userservice.service;

import com.osc.userservice.response.Response;

public interface EmailVerificationService {

     Response isEmailExist(String email);
}
