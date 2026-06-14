package com.eggdepot.repository;

import com.eggdepot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByEggType(Product.EggType eggType);
    
    List<Product> findBySize(Product.EggSize size);
    
    List<Product> findByEggTypeAndSize(Product.EggType eggType, Product.EggSize size);
    
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    List<Product> findByStockQuantityGreaterThan(Integer quantity);
    
    List<Product> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT p FROM Product p WHERE p.stockQuantity > 0 ORDER BY p.price ASC")
    List<Product> findAvailableProductsSortedByPrice();
    
    @Query("SELECT p FROM Product p WHERE p.stockQuantity > 0 ORDER BY p.createdAt DESC")
    List<Product> findNewestAvailableProducts();
    
    @Query("SELECT p FROM Product p WHERE p.stockQuantity <= 10 ORDER BY p.stockQuantity ASC")
    List<Product> findLowStockProducts();
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.stockQuantity > 0")
    Long countAvailableProducts();
    
    @Query("SELECT p.eggType, COUNT(p), AVG(p.price) FROM Product p WHERE p.stockQuantity > 0 GROUP BY p.eggType")
    List<Object[]> getProductStatsByEggType();
    
    @Query("SELECT p.size, COUNT(p), AVG(p.price) FROM Product p WHERE p.stockQuantity > 0 GROUP BY p.size")
    List<Object[]> getProductStatsBySize();
}
