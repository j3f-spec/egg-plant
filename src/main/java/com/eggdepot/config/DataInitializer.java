package com.eggdepot.config;

import com.eggdepot.model.*;
import com.eggdepot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// @Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private PromotionService promotionService;
    
    @Override
    public void run(String... args) throws Exception {
        initializeProducts();
        initializeCustomers();
        initializePromotions();
    }
    
    private void initializeProducts() {
        if (productService.getAllProducts().isEmpty()) {
            Product product1 = new Product();
            product1.setName("Fresh Chicken Eggs - Large");
            product1.setDescription("Premium quality large chicken eggs from free-range hens");
            product1.setPrice(new BigDecimal("4.99"));
            product1.setStockQuantity(150);
            product1.setEggType(Product.EggType.CHICKEN);
            product1.setSize(Product.EggSize.LARGE);
            productService.saveProduct(product1);
            
            Product product2 = new Product();
            product2.setName("Organic Chicken Eggs - Medium");
            product2.setDescription("Certified organic medium chicken eggs");
            product2.setPrice(new BigDecimal("5.99"));
            product2.setStockQuantity(80);
            product2.setEggType(Product.EggType.ORGANIC);
            product2.setSize(Product.EggSize.MEDIUM);
            productService.saveProduct(product2);
            
            Product product3 = new Product();
            product3.setName("Duck Eggs - Extra Large");
            product3.setDescription("Rich and flavorful extra large duck eggs");
            product3.setPrice(new BigDecimal("7.99"));
            product3.setStockQuantity(60);
            product3.setEggType(Product.EggType.DUCK);
            product3.setSize(Product.EggSize.EXTRA_LARGE);
            productService.saveProduct(product3);
            
            Product product4 = new Product();
            product4.setName("Quail Eggs - Small");
            product4.setDescription("Delicate small quail eggs, perfect for gourmet dishes");
            product4.setPrice(new BigDecimal("3.49"));
            product4.setStockQuantity(200);
            product4.setEggType(Product.EggType.QUAIL);
            product4.setSize(Product.EggSize.SMALL);
            productService.saveProduct(product4);
            
            Product product5 = new Product();
            product5.setName("Free Range Chicken Eggs - Jumbo");
            product5.setDescription("Extra large jumbo eggs from free-range chickens");
            product5.setPrice(new BigDecimal("6.99"));
            product5.setStockQuantity(40);
            product5.setEggType(Product.EggType.FREE_RANGE);
            product5.setSize(Product.EggSize.JUMBO);
            productService.saveProduct(product5);
            
            Product product6 = new Product();
            product6.setName("Goose Eggs - Large");
            product6.setDescription("Large goose eggs with rich flavor");
            product6.setPrice(new BigDecimal("9.99"));
            product6.setStockQuantity(25);
            product6.setEggType(Product.EggType.GOOSE);
            product6.setSize(Product.EggSize.LARGE);
            productService.saveProduct(product6);
        }
    }
    
    private void initializeCustomers() {
        if (customerService.getAllCustomers().isEmpty()) {
            Customer customer1 = new Customer();
            customer1.setFirstName("John");
            customer1.setLastName("Smith");
            customer1.setEmail("john.smith@email.com");
            customer1.setPhone("+1-555-0101");
            customer1.setAddress("123 Main St, New York, NY 10001");
            customerService.saveCustomer(customer1);
            
            Customer customer2 = new Customer();
            customer2.setFirstName("Jane");
            customer2.setLastName("Johnson");
            customer2.setEmail("jane.johnson@email.com");
            customer2.setPhone("+1-555-0102");
            customer2.setAddress("456 Oak Ave, Los Angeles, CA 90001");
            customerService.saveCustomer(customer2);
            
            Customer customer3 = new Customer();
            customer3.setFirstName("Bob");
            customer3.setLastName("Wilson");
            customer3.setEmail("bob.wilson@email.com");
            customer3.setPhone("+1-555-0103");
            customer3.setAddress("789 Pine Rd, Chicago, IL 60007");
            customerService.saveCustomer(customer3);
            
            Customer customer4 = new Customer();
            customer4.setFirstName("Alice");
            customer4.setLastName("Brown");
            customer4.setEmail("alice.brown@email.com");
            customer4.setPhone("+1-555-0104");
            customer4.setAddress("321 Elm St, Houston, TX 77001");
            customerService.saveCustomer(customer4);
            
            Customer customer5 = new Customer();
            customer5.setFirstName("Charlie");
            customer5.setLastName("Davis");
            customer5.setEmail("charlie.davis@email.com");
            customer5.setPhone("+1-555-0105");
            customer5.setAddress("654 Maple Dr, Phoenix, AZ 85001");
            customerService.saveCustomer(customer5);
        }
    }
    
    private void initializePromotions() {
        if (promotionService.getAllPromotions().isEmpty()) {
            Promotion promotion1 = new Promotion();
            promotion1.setCode("WELCOME10");
            promotion1.setName("Welcome Discount");
            promotion1.setDescription("10% off your first order");
            promotion1.setType(Promotion.PromotionType.PERCENTAGE_DISCOUNT);
            promotion1.setDiscountValue(new BigDecimal("10"));
            promotion1.setMinimumOrderAmount(new BigDecimal("20"));
            promotion1.setStartDate(LocalDateTime.now());
            promotion1.setEndDate(LocalDateTime.now().plusMonths(3));
            promotion1.setUsageLimit(100);
            promotionService.savePromotion(promotion1);
            
            Promotion promotion2 = new Promotion();
            promotion2.setCode("FREESHIP");
            promotion2.setName("Free Shipping");
            promotion2.setDescription("Free shipping on orders over $50");
            promotion2.setType(Promotion.PromotionType.FREE_SHIPPING);
            promotion2.setDiscountValue(new BigDecimal("10"));
            promotion2.setMinimumOrderAmount(new BigDecimal("50"));
            promotion2.setStartDate(LocalDateTime.now());
            promotion2.setEndDate(LocalDateTime.now().plusMonths(2));
            promotion2.setUsageLimit(200);
            promotionService.savePromotion(promotion2);
            
            Promotion promotion3 = new Promotion();
            promotion3.setCode("EGGDEAL5");
            promotion3.setName("Egg Special");
            promotion3.setDescription("$5 off orders over $30");
            promotion3.setType(Promotion.PromotionType.FIXED_AMOUNT_DISCOUNT);
            promotion3.setDiscountValue(new BigDecimal("5"));
            promotion3.setMinimumOrderAmount(new BigDecimal("30"));
            promotion3.setStartDate(LocalDateTime.now());
            promotion3.setEndDate(LocalDateTime.now().plusMonths(1));
            promotion3.setUsageLimit(150);
            promotionService.savePromotion(promotion3);
        }
    }
}
