package com.osc.userdataservice.service;

import com.session.VerifyCredentialsRequest;
import com.session.VerifyCredentialsResponse;

public interface VerifyUserCredentialsService {

     VerifyCredentialsResponse verifyCredentialsFromDB(VerifyCredentialsRequest verifyCredentialsRequest);
}
