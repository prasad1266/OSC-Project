package com.osc.userservice.controller;

import com.osc.userservice.response.Response;
import com.osc.userservice.service.EmailVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class EmailVerificationController {

    private EmailVerificationService emailVerificationService;

    public EmailVerificationController(EmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @PostMapping("/users/forgotpassword")
    public ResponseEntity<Response> EmailVerification(@RequestParam String email){

        Response response = emailVerificationService.isEmailExist(email);
        return ResponseEntity.ok(response);
    }
}
