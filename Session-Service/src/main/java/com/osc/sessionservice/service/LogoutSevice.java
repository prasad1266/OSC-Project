package com.osc.sessionservice.service;

import com.osc.sessionservice.dto.Logoutdto;
import com.osc.sessionservice.response.Response;

public interface LogoutSevice {
     Response logoutUser(Logoutdto logoutdto);
}
