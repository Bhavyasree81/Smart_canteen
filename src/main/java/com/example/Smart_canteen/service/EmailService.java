package com.example.Smart_canteen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtp(String email, String otp) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("OTP Verification - Smart Canteen");
            message.setText("Your OTP is: " + otp);

            mailSender.send(message);

            System.out.println("✅ OTP sent successfully to: " + email);

        } catch (Exception e) {
            System.out.println("❌ ERROR SENDING MAIL");
            e.printStackTrace(); // 🔥 THIS WILL SHOW REAL ERROR IN LOGS
        }
    }
}








//package com.example.Smart_canteen.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendOtp(String email, String otp) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email);
//        message.setSubject("OTP Verification - Smart Canteen");
//        message.setText("Your OTP is: " + otp);
//
//        mailSender.send(message);
//        System.out.println("Sending OTP to: " + email);
//    }
//}