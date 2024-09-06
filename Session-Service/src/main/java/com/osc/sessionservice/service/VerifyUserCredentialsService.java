package com.osc.sessionservice.service;

import com.osc.sessionservice.dto.Logindto;
import com.osc.sessionservice.dto.VerifyCredentialsResponseDto;

public interface VerifyUserCredentialsService {

     VerifyCredentialsResponseDto fetchUserCredentials(Logindto logindto);
}
