package com.example.Smart_canteen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.Smart_canteen.model.User;
import com.example.Smart_canteen.model.EmailOtp;
import com.example.Smart_canteen.repository.UserRepository;
import com.example.Smart_canteen.repository.EmailOtpRepository;
import com.example.Smart_canteen.service.AuthService;
import com.example.Smart_canteen.service.EmailService;
import com.example.Smart_canteen.service.OtpService;
import com.example.Smart_canteen.service.EmailOtpService;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailOtpService emailOtpService;   // ✅ NEW

    @Autowired
    private EmailOtpRepository emailOtpRepository; // ✅ NEW

    // ================= LOGIN =================

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session) {

        User user = authService.authenticate(email, password);

        if(user != null){
            session.setAttribute("email", user.getEmail());
            session.setAttribute("role", user.getRole());
            session.setAttribute("name", user.getName());

            if(user.getRole().equals("ADMIN")){
                return "redirect:/admin/dashboard";
            }
            else if(user.getRole().equals("FACULTY")){
                return "redirect:/faculty/dashboard";
            }
            else{
                return "redirect:/menu";
            }
        }

        return "login";
    }
    
 // ================= REGISTER PAGE =================

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    // ================= SEND OTP =================

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email,
                          @RequestParam String name,
                          HttpSession session) {

        String otp = otpService.generateOtp();

        // ✅ Save OTP in DB
        emailOtpService.saveOrUpdateOtp(email, otp);

        // ✅ Send Email
        emailService.sendOtp(email, otp);

        session.setAttribute("email", email);
        session.setAttribute("name", name);

        return "verify-otp";
    }

    @GetMapping("/verify-otp")
    public String showVerifyOtpPage() {
        return "verify-otp";
    }

    // ================= VERIFY OTP =================

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String otp,
                            HttpSession session) {

        String email = (String) session.getAttribute("email");

        Optional<EmailOtp> otpData = emailOtpRepository.findByEmail(email);

        if(otpData.isPresent()) {
            EmailOtp record = otpData.get();

            // ✅ Check OTP match
            if(record.getOtp().equals(otp)) {

                // ✅ Check expiry
                if(record.getExpiryTime().isAfter(LocalDateTime.now())) {

                    return "reset-password"; // success
                }
            }
        }

        return "verify-otp"; // fail
    }

    // ================= SET PASSWORD =================

    @PostMapping("/set-password")
    public String setPassword(@RequestParam String password,
                              @RequestParam String confirmPassword,
                              HttpSession session) {

        if(!password.equals(confirmPassword)){
            return "reset-password";
        }

        String email = (String) session.getAttribute("email");
        String name = (String) session.getAttribute("name");

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("STUDENT");
        user.setName(name);

        userRepository.save(user);

        return "redirect:/login";
    }

    // ================= LOGOUT =================

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}








//package com.example.Smart_canteen.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import com.example.Smart_canteen.model.User;
//import com.example.Smart_canteen.repository.UserRepository;
//import com.example.Smart_canteen.service.AuthService;
//import com.example.Smart_canteen.service.EmailService;
//import com.example.Smart_canteen.service.OtpService;
//
//import jakarta.servlet.http.HttpSession;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Controller
//public class AuthController {
//
//    @Autowired
//    private OtpService otpService;
//    
//    @Autowired
//    private EmailService emailService;
//    
//    @Autowired
//    private AuthService authService;
//    
//    @Autowired
//    private UserRepository userRepository;
//
//    // OTP store (fix for session issue)
//    private static Map<String, String> otpStore = new HashMap<>();
//
//    @GetMapping("/register")
//    public String showRegisterPage() {
//        return "register";
//    }
//
//    @GetMapping("/logout")
//    public String logout(HttpSession session){
//        session.invalidate();
//        return "redirect:/";
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestParam String email,
//                        @RequestParam String password,
//                        HttpSession session) {
//
//        User user = authService.authenticate(email, password);
//
//        if(user != null){
//            session.setAttribute("email", user.getEmail());
//            session.setAttribute("role", user.getRole());
//            session.setAttribute("name", user.getName());
//
//            if(user.getRole().equals("ADMIN")){
//                return "redirect:/admin/dashboard";
//            }
//            else if(user.getRole().equals("FACULTY")){
//                return "redirect:/faculty/dashboard";
//            }
//            else{
//                return "redirect:/menu";
//            }
//        }
//
//        return "login";
//    }
//
//    // SEND OTP
//    @PostMapping("/send-otp")
//    public String sendOtp(@RequestParam String email,
//                          @RequestParam String name,
//                          HttpSession session) {
//
//        String otp = otpService.generateOtp();
//
//        // send email
//        emailService.sendOtp(email, otp);
//
//        // store OTP
//        otpStore.put(email, otp);
//
//        session.setAttribute("email", email);
//        session.setAttribute("name", name);
//
//        return "verify-otp";
//    }
//
//    @GetMapping("/verify-otp")
//    public String showVerifyOtpPage() {
//        return "verify-otp";
//    }
//
//    // VERIFY OTP
//    @PostMapping("/verify-otp")
//    public String verifyOtp(@RequestParam String otp,
//                            HttpSession session) {
//
//        String email = (String) session.getAttribute("email");
//        String storedOtp = otpStore.get(email);
//
//        if(storedOtp != null && storedOtp.equals(otp)){
//            otpStore.remove(email);
//            return "reset-password";
//        }
//
//        return "verify-otp";
//    }
//
//    // SET PASSWORD
//    @PostMapping("/set-password")
//    public String setPassword(@RequestParam String password,
//                              @RequestParam String confirmPassword,
//                              HttpSession session) {
//
//        if(!password.equals(confirmPassword)){
//            return "reset-password";
//        }
//
//        String email = (String) session.getAttribute("email");
//        String name = (String) session.getAttribute("name");
//
//        User user = new User();
//        user.setEmail(email);
//        user.setPassword(password);
//        user.setRole("STUDENT");
//        user.setName(name);
//
//        userRepository.save(user);
//
//        return "redirect:/login";
//    }
//}
//
//
//
//
//
//
//
//
////package com.example.Smart_canteen.controller;
////
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Controller;
////import org.springframework.web.bind.annotation.*;
////
////import com.example.Smart_canteen.model.User;
////import com.example.Smart_canteen.repository.UserRepository;
////import com.example.Smart_canteen.service.AuthService;
////import com.example.Smart_canteen.service.EmailService;
////import com.example.Smart_canteen.service.OtpService;
////
////import jakarta.servlet.http.HttpSession;
////@Controller
////public class AuthController {
////
////    @Autowired
////    private OtpService otpService;
////    
////    @Autowired
////    private EmailService emailService;
////    
////    @Autowired
////    private AuthService authService;
////    
////    @Autowired
////    private UserRepository userRepository;
////    
////    
////    
////    @GetMapping("/register")
////    public String showRegisterPage() {
////        return "register";
////    }
////    
////    @GetMapping("/logout")
////    public String logout(HttpSession session){
////        session.invalidate();
////        return "redirect:/";
////    }
////    
////    @PostMapping("/login")
////    public String login(@RequestParam String email,
////                        @RequestParam String password,
////                        HttpSession session) {
////
////        User user = authService.authenticate(email, password);
////
////        if(user != null){
////
////            // Store user data in session
////            session.setAttribute("email", user.getEmail());
////            session.setAttribute("role", user.getRole());
////            
////            session.setAttribute("name", user.getName());
////
////            // Redirect based on role
////            if(user.getRole().equals("ADMIN")){
////                return "redirect:/admin/dashboard";
////            }
////            else if(user.getRole().equals("FACULTY")){
////                return "redirect:/faculty/dashboard";
////            }
////            else{
////                return "redirect:/menu";
////            }
////        }
////
////        return "login";
////    }
////    
////    @PostMapping("/send-otp")
////    public String sendOtp(@RequestParam String email,
////                          @RequestParam String name,
////                          
////                          HttpSession session) {
////
////        String otp = otpService.generateOtp();
////
////        emailService.sendOtp(email, otp);
////        session.setAttribute("email", email);
////        session.setAttribute("name", name);
////
////
////        return "verify-otp";
////    }
////
////
////    
////    @GetMapping("/verify-otp")
////    public String showVerifyOtpPage() {
////        return "verify-otp";
////    }
//////    
//////    @PostMapping("/verify-otp")
//////    public String verifyOtp(@RequestParam String otp) {
//////        return "reset-password";
//////    }
////    @PostMapping("/verify-otp")
////    public String verifyOtp(@RequestParam String otp,
////                            HttpSession session) {
////
////        String storedOtp = (String) session.getAttribute("otp");
////
////        if(storedOtp != null && storedOtp.equals(otp)){
////            return "reset-password";
////        }
////
////        return "verify-otp"; // wrong OTP
////    }
////    
////    
////    @PostMapping("/set-password")
////    public String setPassword(@RequestParam String password,
////                              @RequestParam String confirmPassword,
////                              HttpSession session) {
////
////        if(!password.equals(confirmPassword)){
////            return "reset-password";
////        }
////
////        String email = (String) session.getAttribute("email");
////        String name = (String) session.getAttribute("name");
////
////        User user = new User();
////        user.setEmail(email);
////        user.setPassword(password);
////        user.setRole("STUDENT");
////        user.setName(name);
////
////        userRepository.save(user);
////
////        return "redirect:/login";
////    }
//// 
////}
////
//////@PostMapping("/send-otp")
//////public String sendOtp(@RequestParam String email) {
//////
//////  String otp = otpService.generateOtp();
//////
//////  System.out.println("Generated OTP: " + otp);
//////  
//////  emailService.sendOtp(email, otp);
//////
//////
//////  return "verify-otp";
//////}
//////@PostMapping("/register")
//////public String registerUser(
//////      @RequestParam String nickname,
//////      @RequestParam String email,
//////      @RequestParam String password) {
//////
//////  User user = new User();
//////  user.setNickname(nickname);
//////  user.setEmail(email);
//////  user.setPassword(password);
//////
//////  UserRepository.save(user);
//////
//////  return "redirect:/login";
//////}
////
//////@PostMapping("/verify-otp")
//////public String verifyOtp(@RequestParam String otp) {
//////
//////  System.out.println("Entered OTP: " + otp);
//////
//////  // here you will compare OTP later
//////  // if correct → redirect to reset password
//////
//////  return "reset-password";
//////}
////
//////@PostMapping("/set-password")
//////public String setPassword(@RequestParam String password,
//////                        @RequestParam String confirmPassword) {
//////
//////  if(!password.equals(confirmPassword)){
//////      return "reset-password";
//////  }
//////
//////  // save password in database here
//////
//////  System.out.println("Password updated successfully");
//////
//////  return "redirect:/";
//////}
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
//////package com.example.Smart_canteen.controller;
//////
//////import org.springframework.beans.factory.annotation.Autowired;
//////import org.springframework.stereotype.Controller;
//////import org.springframework.web.bind.annotation.*;
//////
//////import com.example.Smart_canteen.model.User;
//////import com.example.Smart_canteen.service.AuthService;
//////import com.example.Smart_canteen.service.EmailService;
//////import com.example.Smart_canteen.service.OtpService;
//////
//////import jakarta.servlet.http.HttpSession;
//////@Controller
//////public class AuthController {
//////
//////    @Autowired
//////    private OtpService otpService;
//////    
//////    @Autowired
//////    private EmailService emailService;
//////    
//////    @Autowired
//////    private AuthService authService;
//////    
//////    @GetMapping("/register")
//////    public String showRegisterPage() {
//////        return "register";
//////    }
//////    
//////    
//////
//////    @PostMapping("/send-otp")
//////    public String sendOtp(@RequestParam String email) {
//////
//////        String otp = otpService.generateOtp();
//////
//////        System.out.println("Generated OTP: " + otp);
//////        
//////        emailService.sendOtp(email, otp);
//////
//////
//////        return "verify-otp";
//////    }
//////    
//////    @GetMapping("/verify-otp")
//////    public String showVerifyOtpPage() {
//////        return "verify-otp";
//////    }
//////    
//////    @PostMapping("/verify-otp")
//////    public String verifyOtp(@RequestParam String otp) {
//////        return "reset-password";
//////    }
////////    @PostMapping("/verify-otp")
////////    public String verifyOtp(@RequestParam String otp) {
////////
////////        System.out.println("Entered OTP: " + otp);
////////
////////        // here you will compare OTP later
////////        // if correct → redirect to reset password
////////
////////        return "reset-password";
////////    }
//////    
//////    @PostMapping("/set-password")
//////    public String setPassword(@RequestParam String password,
//////                              @RequestParam String confirmPassword) {
//////
//////        if(!password.equals(confirmPassword)){
//////            return "reset-password";
//////        }
//////
//////        // save password in database here
//////
//////        System.out.println("Password updated successfully");
//////
//////        return "redirect:/";
//////    }
//////    
//////    @PostMapping("/login")
//////    public String login(@RequestParam String email,
//////                        @RequestParam String password,
//////                        HttpSession session) {
//////
//////        User user = authService.authenticate(email, password);
//////
//////        if(user != null){
//////
//////            // ✅ store user info in session
//////            session.setAttribute("email", user.getEmail());
//////            session.setAttribute("role", user.getRole());
//////
//////            return "redirect:/menu";
//////        }
//////
//////        return "login";
//////    }
//////}