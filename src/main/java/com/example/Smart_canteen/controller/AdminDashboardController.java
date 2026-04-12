package com.example.Smart_canteen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Smart_canteen.model.CanteenStatus;
import com.example.Smart_canteen.repository.CanteenStatusRepository;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final CanteenStatusRepository canteenStatusRepo;

    public AdminDashboardController(CanteenStatusRepository repo) {
        this.canteenStatusRepo = repo;
    }

    @PostMapping("/canteen/open")
    public String openCanteen() {
        CanteenStatus status = canteenStatusRepo.findById(1)
                .orElseGet(() -> {
                    CanteenStatus newStatus = new CanteenStatus();
                    newStatus.setId(1);
                    return newStatus;
                });
        status.setOpen(true);
        canteenStatusRepo.save(status);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/canteen/close")
    public String closeCanteen() {
        CanteenStatus status = canteenStatusRepo.findById(1)
                .orElseGet(() -> {
                    CanteenStatus newStatus = new CanteenStatus();
                    newStatus.setId(1);
                    return newStatus;
                });
        status.setOpen(false);
        canteenStatusRepo.save(status);
        return "redirect:/admin/dashboard";
    }
}
