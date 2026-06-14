package com.eggdepot.controller;

import com.eggdepot.model.Promotion;
import com.eggdepot.service.PromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/promotions")
@CrossOrigin(origins = "*")
public class PromotionController {
    
    @Autowired
    private PromotionService promotionService;
    
    @GetMapping
    public ResponseEntity<List<Promotion>> getAllPromotions() {
        List<Promotion> promotions = promotionService.getAllPromotions();
        return ResponseEntity.ok(promotions);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable Long id) {
        Optional<Promotion> promotion = promotionService.getPromotionById(id);
        return promotion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<Promotion> getPromotionByCode(@PathVariable String code) {
        Optional<Promotion> promotion = promotionService.getPromotionByCode(code);
        return promotion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Promotion>> getActivePromotions() {
        List<Promotion> promotions = promotionService.getActivePromotions();
        return ResponseEntity.ok(promotions);
    }
    
    @GetMapping("/expired")
    public ResponseEntity<List<Promotion>> getExpiredPromotions() {
        List<Promotion> promotions = promotionService.getExpiredPromotions();
        return ResponseEntity.ok(promotions);
    }
    
    @GetMapping("/upcoming")
    public ResponseEntity<List<Promotion>> getUpcomingPromotions() {
        List<Promotion> promotions = promotionService.getUpcomingPromotions();
        return ResponseEntity.ok(promotions);
    }
    
    @GetMapping("/global")
    public ResponseEntity<List<Promotion>> getGlobalPromotions() {
        List<Promotion> promotions = promotionService.getGlobalPromotions();
        return ResponseEntity.ok(promotions);
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Promotion>> getCustomerSpecificPromotions(@PathVariable Long customerId) {
        List<Promotion> promotions = promotionService.getCustomerSpecificPromotions(customerId);
        return ResponseEntity.ok(promotions);
    }
    
    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@Valid @RequestBody Promotion promotion) {
        if (promotionService.isCodeExists(promotion.getCode())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Promotion savedPromotion = promotionService.savePromotion(promotion);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPromotion);
    }
    
    @PostMapping("/quick")
    public ResponseEntity<Promotion> createQuickPromotion(
            @RequestParam String code,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Promotion.PromotionType type,
            @RequestParam BigDecimal discountValue,
            @RequestParam BigDecimal minimumOrderAmount,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            @RequestParam(required = false) Integer usageLimit) {
        if (promotionService.isCodeExists(code)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Promotion promotion = promotionService.createPromotion(
            code, name, description, type, discountValue, 
            minimumOrderAmount, startDate, endDate, usageLimit
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(promotion);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Promotion> updatePromotion(@PathVariable Long id, 
                                                     @Valid @RequestBody Promotion promotionDetails) {
        Promotion updatedPromotion = promotionService.updatePromotion(id, promotionDetails);
        if (updatedPromotion != null) {
            return ResponseEntity.ok(updatedPromotion);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/validate")
    public ResponseEntity<PromotionValidationResponse> validatePromotion(
            @RequestParam String code,
            @RequestParam BigDecimal orderAmount) {
        Optional<Promotion> promotion = promotionService.validatePromotion(code, orderAmount);
        if (promotion.isPresent()) {
            Promotion validPromotion = promotion.get();
            BigDecimal discountAmount = promotionService.calculateDiscount(validPromotion, orderAmount);
            return ResponseEntity.ok(new PromotionValidationResponse(validPromotion, discountAmount, true));
        }
        return ResponseEntity.ok(new PromotionValidationResponse(null, BigDecimal.ZERO, false));
    }
    
    @GetMapping("/check-code/{code}")
    public ResponseEntity<Boolean> checkCodeExists(@PathVariable String code) {
        boolean exists = promotionService.isCodeExists(code);
        return ResponseEntity.ok(exists);
    }
    
    @GetMapping("/stats/active-count")
    public ResponseEntity<Long> countActivePromotions() {
        Long count = promotionService.countActivePromotions();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/stats/by-type")
    public ResponseEntity<List<Object[]>> getPromotionStatsByType() {
        List<Object[]> stats = promotionService.getPromotionStatsByType();
        return ResponseEntity.ok(stats);
    }
    
    public static class PromotionValidationResponse {
        private Promotion promotion;
        private BigDecimal discountAmount;
        private boolean valid;
        
        public PromotionValidationResponse(Promotion promotion, BigDecimal discountAmount, boolean valid) {
            this.promotion = promotion;
            this.discountAmount = discountAmount;
            this.valid = valid;
        }
        
        public Promotion getPromotion() { return promotion; }
        public void setPromotion(Promotion promotion) { this.promotion = promotion; }
        
        public BigDecimal getDiscountAmount() { return discountAmount; }
        public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }
        
        public boolean isValid() { return valid; }
        public void setValid(boolean valid) { this.valid = valid; }
    }
}
