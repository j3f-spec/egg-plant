package com.eggdepot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }
    
    @GetMapping("/products")
    public String adminProducts() {
        return "admin/products";
    }
    
    @GetMapping("/customers")
    public String adminCustomers() {
        return "admin/customers";
    }
    
    @GetMapping("/orders")
    public String adminOrders() {
        return "admin/orders";
    }
    
    @GetMapping("/promotions")
    public String adminPromotions() {
        return "admin/promotions";
    }
    
    @GetMapping("/analytics")
    public String adminAnalytics() {
        return "admin/analytics";
    }
    
    @GetMapping("/users")
    public String adminUsers() {
        return "admin/users";
    }
}
