package com.eggdepot.service;

import com.eggdepot.model.Promotion;
import com.eggdepot.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PromotionService {
    
    @Autowired
    private PromotionRepository promotionRepository;
    
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }
    
    public Optional<Promotion> getPromotionById(Long id) {
        return promotionRepository.findById(id);
    }
    
    public Optional<Promotion> getPromotionByCode(String code) {
        return promotionRepository.findByCode(code);
    }
    
    public List<Promotion> getActivePromotions() {
        return promotionRepository.findActivePromotions(LocalDateTime.now());
    }
    
    public List<Promotion> getExpiredPromotions() {
        return promotionRepository.findExpiredPromotions(LocalDateTime.now());
    }
    
    public List<Promotion> getUpcomingPromotions() {
        return promotionRepository.findUpcomingPromotions(LocalDateTime.now());
    }
    
    public List<Promotion> getGlobalPromotions() {
        return promotionRepository.findGlobalPromotions();
    }
    
    public List<Promotion> getCustomerSpecificPromotions(Long customerId) {
        return promotionRepository.findCustomerSpecificPromotions(customerId, LocalDateTime.now());
    }
    
    public Promotion savePromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }
    
    public Promotion updatePromotion(Long id, Promotion promotionDetails) {
        Optional<Promotion> existingPromotion = promotionRepository.findById(id);
        if (existingPromotion.isPresent()) {
            Promotion promotion = existingPromotion.get();
            promotion.setCode(promotionDetails.getCode());
            promotion.setName(promotionDetails.getName());
            promotion.setDescription(promotionDetails.getDescription());
            promotion.setType(promotionDetails.getType());
            promotion.setDiscountValue(promotionDetails.getDiscountValue());
            promotion.setMinimumOrderAmount(promotionDetails.getMinimumOrderAmount());
            promotion.setStartDate(promotionDetails.getStartDate());
            promotion.setEndDate(promotionDetails.getEndDate());
            promotion.setIsActive(promotionDetails.getIsActive());
            promotion.setUsageLimit(promotionDetails.getUsageLimit());
            return promotionRepository.save(promotion);
        }
        return null;
    }
    
    public void deletePromotion(Long id) {
        promotionRepository.deleteById(id);
    }
    
    public Optional<Promotion> validatePromotion(String code, BigDecimal orderAmount) {
        Optional<Promotion> promotionOpt = promotionRepository.findValidPromotionByCode(code, LocalDateTime.now());
        if (promotionOpt.isPresent()) {
            Promotion promotion = promotionOpt.get();
            if (orderAmount.compareTo(promotion.getMinimumOrderAmount()) >= 0) {
                return promotionOpt;
            }
        }
        return Optional.empty();
    }
    
    public BigDecimal calculateDiscount(Promotion promotion, BigDecimal orderAmount) {
        switch (promotion.getType()) {
            case PERCENTAGE_DISCOUNT:
                return orderAmount.multiply(promotion.getDiscountValue().divide(BigDecimal.valueOf(100)));
            case FIXED_AMOUNT_DISCOUNT:
                return promotion.getDiscountValue().min(orderAmount);
            case BUY_ONE_GET_ONE:
                return orderAmount.multiply(BigDecimal.valueOf(0.5));
            case FREE_SHIPPING:
                return BigDecimal.valueOf(10.00);
            default:
                return BigDecimal.ZERO;
        }
    }
    
    public void incrementUsageCount(Long promotionId) {
        Optional<Promotion> promotionOpt = promotionRepository.findById(promotionId);
        if (promotionOpt.isPresent()) {
            Promotion promotion = promotionOpt.get();
            promotion.setUsageCount(promotion.getUsageCount() + 1);
            promotionRepository.save(promotion);
        }
    }
    
    public boolean isCodeExists(String code) {
        return promotionRepository.findByCode(code).isPresent();
    }
    
    public Long countActivePromotions() {
        return promotionRepository.countActivePromotions(LocalDateTime.now());
    }
    
    public List<Object[]> getPromotionStatsByType() {
        return promotionRepository.getPromotionStatsByType();
    }
    
    public Promotion createPromotion(String code, String name, String description, 
                                   Promotion.PromotionType type, BigDecimal discountValue,
                                   BigDecimal minimumOrderAmount, LocalDateTime startDate,
                                   LocalDateTime endDate, Integer usageLimit) {
        Promotion promotion = new Promotion(code, name, description, type, discountValue, 
                                          minimumOrderAmount, startDate, endDate);
        promotion.setUsageLimit(usageLimit);
        return promotionRepository.save(promotion);
    }
}
