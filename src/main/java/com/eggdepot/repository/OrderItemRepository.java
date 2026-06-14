package com.eggdepot.repository;

import com.eggdepot.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    List<OrderItem> findByOrderId(Long orderId);
    
    List<OrderItem> findByProductId(Long productId);
    
    @Query("SELECT oi.product.id, SUM(oi.quantity), SUM(oi.totalPrice) FROM OrderItem oi GROUP BY oi.product.id ORDER BY SUM(oi.quantity) DESC")
    List<Object[]> getProductSalesStats();
    
    @Query("SELECT oi.product.name, SUM(oi.quantity), SUM(oi.totalPrice) FROM OrderItem oi GROUP BY oi.product.id ORDER BY SUM(oi.quantity) DESC")
    List<Object[]> getTopSellingProducts();
    
    @Query("SELECT COUNT(oi) FROM OrderItem oi")
    Long countTotalOrderItems();
}
