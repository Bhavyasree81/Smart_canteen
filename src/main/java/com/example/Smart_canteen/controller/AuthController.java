@PostMapping("/send-otp")
public String sendOtp(@RequestParam String email,
                      @RequestParam String name,
                      @RequestParam String role,
                      HttpSession session,
                      Model model) {

    // 🔥 DEBUG LOGS
    System.out.println("🔥 OTP request received for email: " + email);

    // Generate OTP
    String otp = otpService.generateOtp();
    System.out.println("🔥 Generated OTP: " + otp);

    try {
        // Save OTP in DB
        emailOtpService.saveOrUpdateOtp(email, otp);
        System.out.println("✅ OTP saved in database");

        // Send email
        emailService.sendOtp(email, otp);
        System.out.println("✅ OTP email sent successfully");

    } catch (Exception e) {
        System.out.println("❌ ERROR sending OTP: " + e.getMessage());
        e.printStackTrace();

        model.addAttribute("error", "Failed to send OTP. Please try again.");
        return "register";
    }

    // Store in session
    session.setAttribute("email", email);
    session.setAttribute("name", name);
    session.setAttribute("role", role);

    return "verify-otp";
}
