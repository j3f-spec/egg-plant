package com.eggdepot.service;

import com.eggdepot.model.*;
import com.eggdepot.repository.OrderRepository;
import com.eggdepot.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private PromotionService promotionService;
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    
    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status);
    }
    
    public List<Order> getRecentOrders() {
        return orderRepository.findRecentOrders();
    }
    
    public Order createOrder(Long customerId, List<OrderItemRequest> itemRequests, 
                           String deliveryAddress, String notes, String promotionCode) {
        Optional<Customer> customerOpt = customerService.getCustomerById(customerId);
        if (!customerOpt.isPresent()) {
            throw new RuntimeException("Customer not found");
        }
        
        Customer customer = customerOpt.get();
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (OrderItemRequest itemRequest : itemRequests) {
            Optional<Product> productOpt = productService.getProductById(itemRequest.getProductId());
            if (!productOpt.isPresent()) {
                throw new RuntimeException("Product not found: " + itemRequest.getProductId());
            }
            
            Product product = productOpt.get();
            if (!productService.checkAvailability(product.getId(), itemRequest.getQuantity())) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItems.add(orderItem);
            
            totalAmount = totalAmount.add(orderItem.getTotalPrice());
        }
        
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (promotionCode != null && !promotionCode.isEmpty()) {
            Optional<Promotion> promotionOpt = promotionService.validatePromotion(promotionCode, totalAmount);
            if (promotionOpt.isPresent()) {
                Promotion promotion = promotionOpt.get();
                discountAmount = promotionService.calculateDiscount(promotion, totalAmount);
                promotionService.incrementUsageCount(promotion.getId());
            }
        }
        
        BigDecimal taxAmount = totalAmount.multiply(BigDecimal.valueOf(0.08));
        BigDecimal finalAmount = totalAmount.subtract(discountAmount).add(taxAmount);
        
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setDiscountAmount(discountAmount);
        order.setTaxAmount(taxAmount);
        order.setFinalAmount(finalAmount);
        order.setDeliveryAddress(deliveryAddress);
        order.setNotes(notes);
        order.setStatus(Order.OrderStatus.PENDING);
        
        Order savedOrder = orderRepository.save(order);
        
        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
            productService.updateStock(item.getProduct().getId(), -item.getQuantity());
        }
        
        return savedOrder;
    }
    
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }
    
    public void cancelOrder(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            if (order.getStatus() == Order.OrderStatus.PENDING || 
                order.getStatus() == Order.OrderStatus.CONFIRMED) {
                
                for (OrderItem item : order.getOrderItems()) {
                    productService.updateStock(item.getProduct().getId(), item.getQuantity());
                }
                
                order.setStatus(Order.OrderStatus.CANCELLED);
                orderRepository.save(order);
            }
        }
    }
    
    public List<Object[]> getOrderStatsByStatus() {
        return orderRepository.getOrderStatsByStatus();
    }
    
    public List<Object[]> getDailySalesStats(LocalDateTime startDate) {
        return orderRepository.getDailySalesStats(startDate);
    }
    
    public List<Object[]> getMonthlySalesStats() {
        return orderRepository.getMonthlySalesStats();
    }
    
    public BigDecimal calculateTotalRevenue() {
        return orderRepository.calculateTotalRevenue();
    }
    
    public BigDecimal calculateRevenueSince(LocalDateTime startDate) {
        return orderRepository.calculateRevenueSince(startDate);
    }
    
    public BigDecimal calculateAverageOrderValue() {
        return orderRepository.calculateAverageOrderValue();
    }
    
    public Long countTotalOrders() {
        return orderRepository.countTotalOrders();
    }
    
    public Long countOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.countOrdersByStatus(status);
    }
    
    public static class OrderItemRequest {
        private Long productId;
        private Integer quantity;
        
        public OrderItemRequest() {}
        
        public OrderItemRequest(Long productId, Integer quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }
        
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
}
