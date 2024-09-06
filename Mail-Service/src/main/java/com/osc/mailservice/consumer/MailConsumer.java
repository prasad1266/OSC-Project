package com.osc.mailservice.consumer;

import avro.OtpMessage;
import com.osc.mailservice.service.MailService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MailConsumer {
    private MailService mailService;
    private OtpMessage otpMessage;

    public MailConsumer(MailService mailService) {
        this.mailService = mailService;
    }

    @KafkaListener(topics = "Prasad-otp-topic", groupId = "OSC")
    public void consumeOtp(ConsumerRecord<String,OtpMessage> consumerRecord) {
        otpMessage = consumerRecord.value();
        if (otpMessage != null) {
            if (otpMessage.getFailedAttempts() == 0) {
                if (otpMessage.getPurpose().toString().equals("REGISTRATION")) {
                    mailService.sendEmailforRegistration(otpMessage.getEmail().toString(), otpMessage.getOtp().toString(), otpMessage.getPurpose().toString());
                } else {
                    mailService.sendEmailforResetPassword(otpMessage.getEmail().toString(), otpMessage.getOtp().toString(), otpMessage.getPurpose().toString());
                }
            }
            System.out.println("Dnd");
        }
    }
}
