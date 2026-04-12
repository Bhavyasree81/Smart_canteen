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
            message.setFrom("swathikottakota02@gmail.com");
            message.setSubject("🔐 OTP Verification - Smart Canteen");
            message.setText("Your One-Time Password (OTP) is: " + otp + "\n\n"
                    + "This OTP will expire in 5 minutes.\n"
                    + "Do not share this code with anyone.\n\n"
                    + "If you didn't request this, please ignore this email.");

            mailSender.send(message);
            System.out.println("✅ Email sent successfully to " + email);

        } catch (Exception e) {
            System.out.println("❌ Email failed to " + email);
            System.out.println("⚠️ OTP (Development Fallback): " + otp);
            e.printStackTrace();
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
//@Service
//public class EmailService {
//
//    @Value("${BREVO_API_KEY}")
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
//            // ✅ SENDER (NOT from ❌)
//            Map<String, String> sender = new HashMap<>();
//            sender.put("email", "swathikottakota02@gmail.com"); // must be verified
//
//            body.put("to", new Object[]{to});
//            body.put("sender", sender);  // 🔥 FINAL FIX
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
//            System.out.println("OTP (fallback): " + otp);
//        }
//    }
//}
//
////package com.example.Smart_canteen.service;
////
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.stereotype.Service;
////import org.springframework.web.client.RestTemplate;
////import org.springframework.http.*;
////
////import java.util.HashMap;
////import java.util.Map;
////
////@Service
////public class EmailService {
////
////    @Value("${API_KEY}")
////    private String apiKey;
////
////    public void sendOtp(String email, String otp) {
////
////        try {
////            String url = "https://api.brevo.com/v3/smtp/email";
////
////            RestTemplate restTemplate = new RestTemplate();
////
////            HttpHeaders headers = new HttpHeaders();
////            headers.setContentType(MediaType.APPLICATION_JSON);
////            headers.set("api-key", apiKey);
////
////            Map<String, Object> body = new HashMap<>();
////
////            // TO
////            Map<String, String> to = new HashMap<>();
////            to.put("email", email);
////
////            // FROM (MANDATORY)
////            Map<String, String> from = new HashMap<>();
////            from.put("email", "yourverifiedemail@gmail.com");
////
////            body.put("to", new Object[]{to});
////            body.put("from", from);
////            body.put("subject", "OTP Verification - Smart Canteen");
////            body.put("textContent", "Your OTP is: " + otp);
////
////            HttpEntity<Map<String, Object>> request =
////                    new HttpEntity<>(body, headers);
////
////            restTemplate.postForEntity(url, request, String.class);
////
////            System.out.println("✅ Email sent successfully");
////
////        } catch (Exception e) {
////            System.out.println("❌ Email failed");
////            e.printStackTrace();
////
////            // fallback
////            System.out.println("OTP (fallback): " + otp);
////        }
////    }
////}
////
////
//////package com.example.Smart_canteen.service;
//////
//////import org.springframework.beans.factory.annotation.Autowired;
//////import org.springframework.mail.SimpleMailMessage;
//////import org.springframework.mail.javamail.JavaMailSender;
//////import org.springframework.stereotype.Service;
//////@Service
//////public class EmailService {
//////
//////    @Autowired
//////    private JavaMailSender mailSender;
//////
//////    public void sendOtp(String email, String otp) {
//////
//////        try {
//////            SimpleMailMessage message = new SimpleMailMessage();
//////            message.setTo(email);
//////            message.setSubject("OTP Verification - Smart Canteen");
//////            message.setText("Your OTP is: " + otp);
//////
////////            mailSender.send(message);
//////         // mailSender.send(message);   ❌ DISABLE THIS
//////
//////            System.out.println("OTP: " + otp);  
//////
//////            System.out.println("✅ OTP sent successfully to: " + email);
//////         // emailService.sendOtp(email, otp);
//////            System.out.println("OTP: " + otp);
//////
//////        } catch (Exception e) {
//////            System.out.println("❌ ERROR SENDING MAIL");
//////            e.printStackTrace(); // 🔥 THIS WILL SHOW REAL ERROR IN LOGS
//////        }
//////    }
//////}
//////
//////
//////
//////
////
////
////
////
//////package com.example.Smart_canteen.service;
//////
//////import org.springframework.beans.factory.annotation.Autowired;
//////import org.springframework.mail.SimpleMailMessage;
//////import org.springframework.mail.javamail.JavaMailSender;
//////import org.springframework.stereotype.Service;
//////
//////@Service
//////public class EmailService {
//////
//////    @Autowired
//////    private JavaMailSender mailSender;
//////
//////    public void sendOtp(String email, String otp) {
//////
//////        SimpleMailMessage message = new SimpleMailMessage();
//////        message.setTo(email);
//////        message.setSubject("OTP Verification - Smart Canteen");
//////        message.setText("Your OTP is: " + otp);
//////
//////        mailSender.send(message);
//////        System.out.println("Sending OTP to: " + email);
//////    }
//////}