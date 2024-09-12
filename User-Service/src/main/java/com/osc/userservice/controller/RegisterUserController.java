package com.osc.userservice.controller;

import com.osc.userservice.dto.*;
import com.osc.userservice.response.Response;

import com.osc.userservice.service.UserRegisterService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
public class RegisterUserController {

    private static final Logger logger = LogManager.getLogger(RegisterUserController.class);

private UserRegisterService userRegisterService;


    public RegisterUserController(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }

    @PostMapping("/user/signup")
    public ResponseEntity<Response> registerUser(@RequestBody  UserDto userDto) {
        logger.info("Received signup request for user: {}");

        Response response = userRegisterService.registerUser(userDto);
        logger.info("User registration successful : {}",response);
        return ResponseEntity.ok(response);
    }
}
