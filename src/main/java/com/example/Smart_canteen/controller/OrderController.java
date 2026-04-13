package com.example.Smart_canteen.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.Smart_canteen.model.CanteenStatus;
import com.example.Smart_canteen.model.Order;
import com.example.Smart_canteen.model.OrderStatus;
import com.example.Smart_canteen.repository.CanteenStatusRepository;
import com.example.Smart_canteen.repository.OrderRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

    private final OrderRepository orderRepository;
    private final CanteenStatusRepository canteenStatusRepo;

    public OrderController(OrderRepository orderRepository,
                           CanteenStatusRepository canteenStatusRepo) {
        this.orderRepository = orderRepository;
        this.canteenStatusRepo = canteenStatusRepo;
    }

    @GetMapping("/order-success")
    public String orderSuccess(HttpSession session, Model model) {

        String name = (String) session.getAttribute("name");

        if (name == null || name.isEmpty()) {
            name = "Hungry Hero";
        }

        model.addAttribute("name", name);

        return "order-success";
    }

    @PostMapping("/order/place")
    public String placeOrder(HttpSession session) {

        String email = (String) session.getAttribute("email");
        String role = (String) session.getAttribute("role");

        if (email == null) {
            return "redirect:/login";
        }

        if (role == null || role.isEmpty()) {
            role = "STUDENT";
        }

        CanteenStatus status = canteenStatusRepo.findById(1).orElse(null);

        if (status == null || !status.isOpen()) {
            return "redirect:/canteen-closed";
        }

        Order order = new Order();
        order.setUserEmail(email);
        order.setRole(role);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        orderRepository.save(order);

        return "redirect:/order-success";
    }
}