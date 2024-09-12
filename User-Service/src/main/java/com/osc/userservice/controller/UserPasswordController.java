package com.osc.userservice.controller;

import com.osc.userservice.dto.PasswordDto;
import com.osc.userservice.dto.UpdatePasswordDto;
import com.osc.userservice.response.Response;

import com.osc.userservice.service.UserPasswordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class UserPasswordController {

    private UserPasswordService userPasswordService;

    Logger logger = LogManager.getLogger(UserPasswordController.class);
    public UserPasswordController(UserPasswordService userPasswordService) {
        this.userPasswordService = userPasswordService;
    }

    @PostMapping("/user/createPassword")
    public ResponseEntity<Response> createPassword(@RequestBody PasswordDto passwordDto){
        logger.info("Received request for Create password for user: {}",passwordDto.getUserId()) ;
        Response response = userPasswordService.createPassword(passwordDto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/user/changePassword")
    public ResponseEntity<Response> updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto){
        logger.info("Received request for forgot password for user: {}",updatePasswordDto.getEmail()) ;
        Response response = userPasswordService.updatePassword(updatePasswordDto);
        return ResponseEntity.ok(response);
    }
}
