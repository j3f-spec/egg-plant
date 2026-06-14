package com.eggdepot.service;

import com.eggdepot.model.Customer;
import com.eggdepot.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }
    
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
    
    public List<Customer> searchCustomers(String keyword) {
        return customerRepository.findByNameContaining(keyword);
    }
    
    public List<Customer> getNewestCustomers() {
        return customerRepository.findNewestCustomers();
    }
    
    public List<Customer> getCustomersWithMostOrders() {
        return customerRepository.findCustomersWithMostOrders();
    }
    
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            customer.setFirstName(customerDetails.getFirstName());
            customer.setLastName(customerDetails.getLastName());
            customer.setEmail(customerDetails.getEmail());
            customer.setPhone(customerDetails.getPhone());
            customer.setAddress(customerDetails.getAddress());
            return customerRepository.save(customer);
        }
        return null;
    }
    
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
    
    public boolean isEmailExists(String email) {
        return customerRepository.findByEmail(email).isPresent();
    }
    
    public Long countTotalCustomers() {
        return customerRepository.countTotalCustomers();
    }
    
    public Long countNewCustomersSince(LocalDateTime startDate) {
        return customerRepository.countNewCustomersSince(startDate);
    }
    
    public List<String> getAllCustomerEmails() {
        return customerRepository.findAllEmails();
    }
    
    public Customer createCustomer(String firstName, String lastName, String email, String phone, String address) {
        Customer customer = new Customer(firstName, lastName, email, phone, address);
        return customerRepository.save(customer);
    }
}
