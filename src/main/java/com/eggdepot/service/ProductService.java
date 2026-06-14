package com.eggdepot.service;

import com.eggdepot.model.Product;
import com.eggdepot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    public List<Product> getAvailableProducts() {
        return productRepository.findAvailableProductsSortedByPrice();
    }
    
    public List<Product> getProductsByEggType(Product.EggType eggType) {
        return productRepository.findByEggType(eggType);
    }
    
    public List<Product> getProductsBySize(Product.EggSize size) {
        return productRepository.findBySize(size);
    }
    
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }
    
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }
    
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    
    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setStockQuantity(productDetails.getStockQuantity());
            product.setEggType(productDetails.getEggType());
            product.setSize(productDetails.getSize());
            product.setImageUrl(productDetails.getImageUrl());
            return productRepository.save(product);
        }
        return null;
    }
    
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    public boolean updateStock(Long productId, Integer quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            int newStock = product.getStockQuantity() + quantity;
            if (newStock >= 0) {
                product.setStockQuantity(newStock);
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }
    
    public boolean checkAvailability(Long productId, Integer requiredQuantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        return productOpt.map(product -> product.getStockQuantity() >= requiredQuantity).orElse(false);
    }
    
    public List<Object[]> getProductStatsByEggType() {
        return productRepository.getProductStatsByEggType();
    }
    
    public List<Object[]> getProductStatsBySize() {
        return productRepository.getProductStatsBySize();
    }
    
    public Long countAvailableProducts() {
        return productRepository.countAvailableProducts();
    }
}
