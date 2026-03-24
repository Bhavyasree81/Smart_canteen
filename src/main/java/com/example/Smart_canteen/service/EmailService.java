package com.example.Smart_canteen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    // ✅ API KEY from Render environment variable
    @Value("${BREVO_API_KEY}")
    private String apiKey;

    public void sendOtp(String email, String otp) {

        try {
            String url = "https://api.brevo.com/v3/smtp/email";

            RestTemplate restTemplate = new RestTemplate();

            // ✅ Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", apiKey);

            // ✅ Request Body
            Map<String, Object> body = new HashMap<>();

            // 🔹 TO (receiver)
            Map<String, String> to = new HashMap<>();
            to.put("email", email);

            // 🔹 FROM (🔥 VERY IMPORTANT - MUST BE VERIFIED IN BREVO)
            Map<String, String> from = new HashMap<>();
            from.put("email", "swathikottakota02@gmail.com"); // ⚠️ CHANGE THIS

            body.put("to", new Object[]{to});
            body.put("from", from); // ✅ FIX (this solves your error)
            body.put("subject", "OTP Verification - Smart Canteen");
            body.put("textContent", "Your OTP is: " + otp);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            // ✅ Send request
            restTemplate.postForEntity(url, request, String.class);

            System.out.println("✅ Email sent successfully");

        } catch (Exception e) {

            System.out.println("❌ Email failed");
            e.printStackTrace();

            // fallback (so you can still test OTP)
            System.out.println("OTP (fallback): " + otp);
        }
    }
}


//package com.example.Smart_canteen.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.http.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class EmailService {
//
//    @Value("${API_KEY}")
//    private String apiKey;
//
//    public void sendOtp(String email, String otp) {
//
//        try {
//            String url = "https://api.brevo.com/v3/smtp/email";
//
//            RestTemplate restTemplate = new RestTemplate();
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set("api-key", apiKey);
//
//            Map<String, Object> body = new HashMap<>();
//
//            // TO
//            Map<String, String> to = new HashMap<>();
//            to.put("email", email);
//
//            // FROM (MANDATORY)
//            Map<String, String> from = new HashMap<>();
//            from.put("email", "yourverifiedemail@gmail.com");
//
//            body.put("to", new Object[]{to});
//            body.put("from", from);
//            body.put("subject", "OTP Verification - Smart Canteen");
//            body.put("textContent", "Your OTP is: " + otp);
//
//            HttpEntity<Map<String, Object>> request =
//                    new HttpEntity<>(body, headers);
//
//            restTemplate.postForEntity(url, request, String.class);
//
//            System.out.println("✅ Email sent successfully");
//
//        } catch (Exception e) {
//            System.out.println("❌ Email failed");
//            e.printStackTrace();
//
//            // fallback
//            System.out.println("OTP (fallback): " + otp);
//        }
//    }
//}
//
//
////package com.example.Smart_canteen.service;
////
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.mail.SimpleMailMessage;
////import org.springframework.mail.javamail.JavaMailSender;
////import org.springframework.stereotype.Service;
////@Service
////public class EmailService {
////
////    @Autowired
////    private JavaMailSender mailSender;
////
////    public void sendOtp(String email, String otp) {
////
////        try {
////            SimpleMailMessage message = new SimpleMailMessage();
////            message.setTo(email);
////            message.setSubject("OTP Verification - Smart Canteen");
////            message.setText("Your OTP is: " + otp);
////
//////            mailSender.send(message);
////         // mailSender.send(message);   ❌ DISABLE THIS
////
////            System.out.println("OTP: " + otp);  
////
////            System.out.println("✅ OTP sent successfully to: " + email);
////         // emailService.sendOtp(email, otp);
////            System.out.println("OTP: " + otp);
////
////        } catch (Exception e) {
////            System.out.println("❌ ERROR SENDING MAIL");
////            e.printStackTrace(); // 🔥 THIS WILL SHOW REAL ERROR IN LOGS
////        }
////    }
////}
////
////
////
////
//
//
//
//
////package com.example.Smart_canteen.service;
////
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.mail.SimpleMailMessage;
////import org.springframework.mail.javamail.JavaMailSender;
////import org.springframework.stereotype.Service;
////
////@Service
////public class EmailService {
////
////    @Autowired
////    private JavaMailSender mailSender;
////
////    public void sendOtp(String email, String otp) {
////
////        SimpleMailMessage message = new SimpleMailMessage();
////        message.setTo(email);
////        message.setSubject("OTP Verification - Smart Canteen");
////        message.setText("Your OTP is: " + otp);
////
////        mailSender.send(message);
////        System.out.println("Sending OTP to: " + email);
////    }
////}