package com.osc.sessionservice.controller;

import com.osc.sessionservice.dto.Logoutdto;
import com.osc.sessionservice.response.Response;
import com.osc.sessionservice.service.LogoutSevice;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
public class LogoutController {

    private  LogoutSevice logoutSevice;

    public LogoutController( LogoutSevice logoutSevice) {
        this.logoutSevice = logoutSevice;
    }

    @PostMapping("/session/logout")
    public Response userLogout(@RequestBody Logoutdto logoutdto){

        Response response =  logoutSevice.logoutUser(logoutdto);
        return response;
    }
}
