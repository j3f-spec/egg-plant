package com.eggdepot.controller;

import com.eggdepot.model.Customer;
import com.eggdepot.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        Optional<Customer> customer = customerService.getCustomerByEmail(email);
        return customer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomers(@RequestParam String keyword) {
        List<Customer> customers = customerService.searchCustomers(keyword);
        return ResponseEntity.ok(customers);
    }
    
    @GetMapping("/newest")
    public ResponseEntity<List<Customer>> getNewestCustomers() {
        List<Customer> customers = customerService.getNewestCustomers();
        return ResponseEntity.ok(customers);
    }
    
    @GetMapping("/top-orders")
    public ResponseEntity<List<Customer>> getCustomersWithMostOrders() {
        List<Customer> customers = customerService.getCustomersWithMostOrders();
        return ResponseEntity.ok(customers);
    }
    
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        if (customerService.isEmailExists(customer.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Customer savedCustomer = customerService.saveCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }
    
    @PostMapping("/quick")
    public ResponseEntity<Customer> createQuickCustomer(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam(required = false) String address) {
        if (customerService.isEmailExists(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Customer customer = customerService.createCustomer(firstName, lastName, email, phone, address);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, 
                                                 @Valid @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/check-email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = customerService.isEmailExists(email);
        return ResponseEntity.ok(exists);
    }
    
    @GetMapping("/stats/total")
    public ResponseEntity<Long> countTotalCustomers() {
        Long count = customerService.countTotalCustomers();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/stats/new-since")
    public ResponseEntity<Long> countNewCustomersSince(@RequestParam LocalDateTime startDate) {
        Long count = customerService.countNewCustomersSince(startDate);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/emails")
    public ResponseEntity<List<String>> getAllCustomerEmails() {
        List<String> emails = customerService.getAllCustomerEmails();
        return ResponseEntity.ok(emails);
    }
}
