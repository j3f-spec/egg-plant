package com.eggdepot.repository;

import com.eggdepot.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    
    Optional<Promotion> findByCode(String code);
    
    List<Promotion> findByIsActive(Boolean isActive);
    
    List<Promotion> findByCustomerId(Long customerId);
    
    List<Promotion> findByType(Promotion.PromotionType type);
    
    @Query("SELECT p FROM Promotion p WHERE p.startDate <= :currentDate AND p.endDate >= :currentDate AND p.isActive = true")
    List<Promotion> findActivePromotions(@Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT p FROM Promotion p WHERE p.code = :code AND p.startDate <= :currentDate AND p.endDate >= :currentDate AND p.isActive = true")
    Optional<Promotion> findValidPromotionByCode(@Param("code") String code, @Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT p FROM Promotion p WHERE p.endDate < :currentDate")
    List<Promotion> findExpiredPromotions(@Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT p FROM Promotion p WHERE p.usageLimit IS NOT NULL AND p.usageCount >= p.usageLimit")
    List<Promotion> findExhaustedPromotions();
    
    @Query("SELECT p FROM Promotion p WHERE p.startDate > :currentDate")
    List<Promotion> findUpcomingPromotions(@Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT COUNT(p) FROM Promotion p WHERE p.isActive = true AND p.startDate <= :currentDate AND p.endDate >= :currentDate")
    Long countActivePromotions(@Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT p.type, COUNT(p) FROM Promotion p GROUP BY p.type")
    List<Object[]> getPromotionStatsByType();
    
    @Query("SELECT p FROM Promotion p WHERE p.customer IS NULL")
    List<Promotion> findGlobalPromotions();
    
    @Query("SELECT p FROM Promotion p WHERE p.customer.id = :customerId AND p.startDate <= :currentDate AND p.endDate >= :currentDate AND p.isActive = true")
    List<Promotion> findCustomerSpecificPromotions(@Param("customerId") Long customerId, @Param("currentDate") LocalDateTime currentDate);
}
