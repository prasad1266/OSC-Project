package com.osc.sessionservice.controller;

import com.osc.sessionservice.dto.Logindto;
import com.osc.sessionservice.response.Response;
import com.osc.sessionservice.service.LoginUserService;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.Logger;

@CrossOrigin("*")
@RestController
public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);
    private LoginUserService loginUserService;

    public LoginController(LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }

    @PostMapping("/user/session")
    public Response userLogin(@RequestBody Logindto logindto) {
        logger.info("In Login Controller");
        Response response = loginUserService.loginUser(logindto);
        return response;
    }
}
