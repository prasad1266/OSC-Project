package com.osc.sessionservice.controller;

import com.osc.sessionservice.dto.Logindto;
import com.osc.sessionservice.response.Response;
import com.osc.sessionservice.service.LoginUserService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class LoginController {

    private LoginUserService loginUserService;

    public LoginController(LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }

    @PostMapping("/user/session")
    public Response userLogin(@RequestBody Logindto logindto){

        Response response =  loginUserService.loginUser(logindto);
        return response;
    }
}
