package com.eggdepot.repository;

import com.eggdepot.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByCustomerId(Long customerId);
    
    List<Order> findByStatus(Order.OrderStatus status);
    
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Order> findByCustomerEmail(String email);
    
    @Query("SELECT o FROM Order o WHERE o.customer.firstName LIKE %:name% OR o.customer.lastName LIKE %:name%")
    List<Order> findByCustomerNameContaining(@Param("name") String name);
    
    @Query("SELECT o FROM Order o ORDER BY o.orderDate DESC")
    List<Order> findRecentOrders();
    
    @Query("SELECT o FROM Order o WHERE o.finalAmount BETWEEN :minAmount AND :maxAmount")
    List<Order> findByAmountRange(@Param("minAmount") BigDecimal minAmount, @Param("maxAmount") BigDecimal maxAmount);
    
    @Query("SELECT COUNT(o) FROM Order o")
    Long countTotalOrders();
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = :status")
    Long countOrdersByStatus(@Param("status") Order.OrderStatus status);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.orderDate >= :startDate")
    Long countOrdersSince(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT SUM(o.finalAmount) FROM Order o WHERE o.status != 'CANCELLED' AND o.status != 'REFUNDED'")
    BigDecimal calculateTotalRevenue();
    
    @Query("SELECT SUM(o.finalAmount) FROM Order o WHERE o.orderDate >= :startDate AND o.status != 'CANCELLED' AND o.status != 'REFUNDED'")
    BigDecimal calculateRevenueSince(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT AVG(o.finalAmount) FROM Order o WHERE o.status != 'CANCELLED' AND o.status != 'REFUNDED'")
    BigDecimal calculateAverageOrderValue();
    
    @Query("SELECT o.status, COUNT(o), SUM(o.finalAmount) FROM Order o GROUP BY o.status")
    List<Object[]> getOrderStatsByStatus();
    
    @Query("SELECT DATE(o.orderDate), COUNT(o), SUM(o.finalAmount) FROM Order o WHERE o.orderDate >= :startDate GROUP BY DATE(o.orderDate) ORDER BY DATE(o.orderDate)")
    List<Object[]> getDailySalesStats(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT MONTH(o.orderDate), YEAR(o.orderDate), COUNT(o), SUM(o.finalAmount) FROM Order o GROUP BY MONTH(o.orderDate), YEAR(o.orderDate) ORDER BY YEAR(o.orderDate), MONTH(o.orderDate)")
    List<Object[]> getMonthlySalesStats();
}
