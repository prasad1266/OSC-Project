package com.osc.sessionservice.controller;

import com.osc.sessionservice.dto.Logindto;
import com.osc.sessionservice.response.Response;
import com.osc.sessionservice.service.LoginUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private LoginUserService loginUserService;

    public LoginController(LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }

    @GetMapping("/user/session")
    public Response userLogin(@RequestBody Logindto logindto){

        Response response =  loginUserService.loginUser(logindto);
        return response;
    }
}
