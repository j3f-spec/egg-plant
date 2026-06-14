package com.eggdepot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @GetMapping("/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }
    
    @GetMapping("/inventory")
    public String userInventory() {
        return "user/inventory";
    }
    
    @GetMapping("/orders")
    public String userOrders() {
        return "user/orders";
    }
    
    @GetMapping("/reviews")
    public String userReviews() {
        return "user/reviews";
    }
    
    @GetMapping("/locations")
    public String userLocations() {
        return "user/locations";
    }
    
    @GetMapping("/payments")
    public String userPayments() {
        return "user/payments";
    }
    
    @GetMapping("/profile")
    public String userProfile() {
        return "user/profile";
    }
}
