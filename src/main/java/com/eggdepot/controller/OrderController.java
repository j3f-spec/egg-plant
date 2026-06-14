package com.eggdepot.controller;

import com.eggdepot.model.Order;
import com.eggdepot.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable Order.OrderStatus status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/recent")
    public ResponseEntity<List<Order>> getRecentOrders() {
        List<Order> orders = orderService.getRecentOrders();
        return ResponseEntity.ok(orders);
    }
    
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        try {
            Order order = orderService.createOrder(
                orderRequest.getCustomerId(),
                orderRequest.getItems(),
                orderRequest.getDeliveryAddress(),
                orderRequest.getNotes(),
                orderRequest.getPromotionCode()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, 
                                                   @RequestParam Order.OrderStatus status) {
        Order updatedOrder = orderService.updateOrderStatus(id, status);
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/stats/status")
    public ResponseEntity<List<Object[]>> getOrderStatsByStatus() {
        List<Object[]> stats = orderService.getOrderStatsByStatus();
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/stats/daily")
    public ResponseEntity<List<Object[]>> getDailySalesStats(
            @RequestParam(required = false) LocalDateTime startDate) {
        if (startDate == null) {
            startDate = LocalDateTime.now().minusDays(30);
        }
        List<Object[]> stats = orderService.getDailySalesStats(startDate);
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/stats/monthly")
    public ResponseEntity<List<Object[]>> getMonthlySalesStats() {
        List<Object[]> stats = orderService.getMonthlySalesStats();
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/stats/revenue/total")
    public ResponseEntity<java.math.BigDecimal> getTotalRevenue() {
        java.math.BigDecimal revenue = orderService.calculateTotalRevenue();
        return ResponseEntity.ok(revenue);
    }
    
    @GetMapping("/stats/revenue/since")
    public ResponseEntity<java.math.BigDecimal> getRevenueSince(@RequestParam LocalDateTime startDate) {
        java.math.BigDecimal revenue = orderService.calculateRevenueSince(startDate);
        return ResponseEntity.ok(revenue);
    }
    
    @GetMapping("/stats/average-order-value")
    public ResponseEntity<java.math.BigDecimal> getAverageOrderValue() {
        java.math.BigDecimal aov = orderService.calculateAverageOrderValue();
        return ResponseEntity.ok(aov);
    }
    
    @GetMapping("/stats/total")
    public ResponseEntity<Long> countTotalOrders() {
        Long count = orderService.countTotalOrders();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/stats/count/{status}")
    public ResponseEntity<Long> countOrdersByStatus(@PathVariable Order.OrderStatus status) {
        Long count = orderService.countOrdersByStatus(status);
        return ResponseEntity.ok(count);
    }
    
    public static class OrderRequest {
        private Long customerId;
        private List<OrderService.OrderItemRequest> items;
        private String deliveryAddress;
        private String notes;
        private String promotionCode;
        
        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }
        
        public List<OrderService.OrderItemRequest> getItems() { return items; }
        public void setItems(List<OrderService.OrderItemRequest> items) { this.items = items; }
        
        public String getDeliveryAddress() { return deliveryAddress; }
        public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
        
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
        
        public String getPromotionCode() { return promotionCode; }
        public void setPromotionCode(String promotionCode) { this.promotionCode = promotionCode; }
    }
}
