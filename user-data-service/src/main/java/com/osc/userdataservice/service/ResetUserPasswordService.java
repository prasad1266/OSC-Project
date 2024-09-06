package com.osc.userdataservice.service;

import com.user.ResetPasswordRequest;
import com.user.ResetPasswordResponse;

public interface ResetUserPasswordService {

     ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest);
}
