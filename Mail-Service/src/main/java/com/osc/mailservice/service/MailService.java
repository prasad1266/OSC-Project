package com.osc.mailservice.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailforRegistration(String email, String otp, String purpose) {
        String subject = "OSC Registration OTP ";
        String text = "Welcome to Online Shopping Cart\n\nDear User,\n\nAnd OTP is: " + otp + "\nThis OTP is for " + purpose + " purpose"+ ".\n\nRegards,\nYour OSC";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("prasadh1266@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
        System.out.println("Mail message: " + message);
    }
    public void sendEmailforResetPassword(String email, String otp, String purpose) {
        String subject = "Your OTP for " + purpose;
        String text = "Dear User,\n\nYour OTP is: " + otp + "\n\nThis OTP is for " + purpose + ".\n\nRegards,\nYour OSC";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("prasadh1266@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
        System.out.println("Mail message: " + message);
    }
}
