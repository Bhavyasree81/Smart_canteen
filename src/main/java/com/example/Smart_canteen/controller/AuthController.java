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
import org.springframework.ui.Model;

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
    private EmailOtpService emailOtpService;

    @Autowired
    private EmailOtpRepository emailOtpRepository;

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
                          @RequestParam String role,
                          HttpSession session,
                          Model model) {

        // Generate OTP
        String otp = otpService.generateOtp();

        // Save OTP in DB
        emailOtpService.saveOrUpdateOtp(email, otp);

        // Send email
        emailService.sendOtp(email, otp);

        // Store in session
        session.setAttribute("email", email);
        session.setAttribute("name", name);
        session.setAttribute("role", role);

        System.out.println("OTP SENT: " + otp); // debug

        return "verify-otp";
    }

    // ================= VERIFY OTP PAGE =================

    @GetMapping("/verify-otp")
    public String showVerifyOtpPage() {
        return "verify-otp";
    }

    // ================= VERIFY OTP =================

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String otp,
                            HttpSession session,
                            Model model) {

        String email = (String) session.getAttribute("email");

        if(email == null) {
            model.addAttribute("error", "Session expired. Please request OTP again.");
            return "verify-otp";
        }

        Optional<EmailOtp> otpData = emailOtpRepository.findByEmail(email);

        if(otpData.isPresent()) {
            EmailOtp record = otpData.get();

            if(record.getOtp().equals(otp)) {

                if(record.getExpiryTime().isAfter(LocalDateTime.now())) {
                    return "redirect:/reset-password";
                } else {
                    model.addAttribute("error", "❌ OTP expired.");
                    return "verify-otp";
                }

            } else {
                model.addAttribute("error", "❌ Invalid OTP.");
                return "verify-otp";
            }
        }

        model.addAttribute("error", "❌ No OTP found.");
        return "verify-otp";
    }

    // ================= RESET PASSWORD PAGE =================

    @GetMapping("/reset-password")
    public String showResetPasswordPage() {
        return "reset-password";
    }

    // ================= SET PASSWORD =================

    @PostMapping("/set-password")
    public String setPassword(@RequestParam String password,
                              @RequestParam String confirmPassword,
                              HttpSession session,
                              Model model) {

        if(!password.equals(confirmPassword)){
            model.addAttribute("error", "❌ Passwords do not match.");
            return "reset-password";
        }

        if(password.length() < 6) {
            model.addAttribute("error", "❌ Password must be at least 6 characters.");
            return "reset-password";
        }

        String email = (String) session.getAttribute("email");
        String name = (String) session.getAttribute("name");
        String role = (String) session.getAttribute("role");

        if(email == null) {
            model.addAttribute("error", "Session expired. Please register again.");
            return "register";
        }

        // fallback role safety
        if(role == null || role.isEmpty()) {
            role = "STUDENT";
        }

        // check if user exists
        User existingUser = userRepository.findByEmail(email);
        if(existingUser != null) {
            model.addAttribute("error", "User already exists. Please login.");
            return "reset-password";
        }

        // create user
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setRole(role);   // ✅ IMPORTANT FIX

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

