@PostMapping("/order/place")
public String placeOrder(HttpSession session) {

    // Debug logs (keep for now)
    System.out.println("SESSION EMAIL = " + session.getAttribute("email"));
    System.out.println("SESSION ROLE = " + session.getAttribute("role"));

    String email = (String) session.getAttribute("email");
    String role = (String) session.getAttribute("role");

    // ✅ Safety check (important)
    if (email == null) {
        return "redirect:/login";
    }

    // ✅ If role missing, set default (prevents null in DB)
    if (role == null || role.isEmpty()) {
        role = "STUDENT";  // default role
    }

    // ✅ Check canteen status
    CanteenStatus status = canteenStatusRepo.findById(1).orElse(null);

    if (status == null || !status.isOpen()) {
        return "redirect:/canteen-closed";
    }

    // ✅ Create order
    Order order = new Order();
    order.setUserEmail(email);
    order.setRole(role);   // 👈 IMPORTANT (Admin will use this)
    order.setOrderTime(LocalDateTime.now());
    order.setStatus(OrderStatus.PLACED);

    orderRepository.save(order);

    return "redirect:/order-success";
}