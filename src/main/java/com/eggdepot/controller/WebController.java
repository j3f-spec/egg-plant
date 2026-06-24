package com.eggdepot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebController {
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/products")
    public String products() {
        return "products";
    }
    
    @GetMapping("/customers")
    public String customers() {
        return "customers";
    }
    
    @GetMapping("/orders")
    public String orders() {
        return "orders";
    }
    
    @GetMapping("/promotions")
    public String promotions() {
        return "promotions";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
