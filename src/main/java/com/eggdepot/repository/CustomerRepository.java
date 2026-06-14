package com.eggdepot.repository;

import com.eggdepot.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findByEmail(String email);
    
    List<Customer> findByFirstNameContainingIgnoreCase(String firstName);
    
    List<Customer> findByLastNameContainingIgnoreCase(String lastName);
    
    List<Customer> findByPhone(String phone);
    
    @Query("SELECT c FROM Customer c WHERE c.firstName LIKE %:name% OR c.lastName LIKE %:name%")
    List<Customer> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT c FROM Customer c WHERE c.email LIKE %:email%")
    List<Customer> findByEmailContaining(@Param("email") String email);
    
    @Query("SELECT COUNT(c) FROM Customer c")
    Long countTotalCustomers();
    
    @Query("SELECT COUNT(c) FROM Customer c WHERE c.createdAt >= :startDate")
    Long countNewCustomersSince(@Param("startDate") java.time.LocalDateTime startDate);
    
    @Query("SELECT c FROM Customer c ORDER BY c.createdAt DESC")
    List<Customer> findNewestCustomers();
    
    @Query("SELECT c FROM Customer c WHERE SIZE(c.orders) > 0 ORDER BY SIZE(c.orders) DESC")
    List<Customer> findCustomersWithMostOrders();
    
    @Query("SELECT c FROM Customer c WHERE SIZE(c.orders) = 0")
    List<Customer> findCustomersWithNoOrders();
    
    @Query("SELECT c.email FROM Customer c")
    List<String> findAllEmails();
}
