package com.osc.sessionservice.service;

import com.osc.sessionservice.dto.Logindto;
import com.osc.sessionservice.response.Response;

public interface LoginUserService {
     Response loginUser(Logindto logindto);
}
