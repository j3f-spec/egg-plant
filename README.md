# Egg Depot Sales & Marketing System

A comprehensive Spring Boot web application for managing sales and marketing operations for an egg depot business.

## Features

### 📊 Sales Management
- Complete order management system
- Real-time inventory tracking
- Customer relationship management
- Sales analytics and reporting
- Dashboard with key metrics

### 🥚 Product Management
- Manage different egg types (Chicken, Duck, Quail, Goose, Organic, Free Range)
- Size variations (Small, Medium, Large, Extra Large, Jumbo)
- Stock level monitoring with low-stock alerts
- Price management
- Product categorization and search

### 👥 Customer Management
- Customer registration and profiles
- Order history tracking
- Contact management
- Customer segmentation

### 🎯 Marketing Features
- Promotion and discount management
- Multiple promotion types (Percentage, Fixed Amount, BOGO, Free Shipping)
- Usage tracking and limits
- Customer-specific promotions

### 📈 Analytics & Reporting
- Sales trend analysis
- Revenue tracking
- Order status monitoring
- Top-selling products
- Customer analytics

## Technology Stack

- **Backend**: Spring Boot 3.2.5
- **Database**: H2 (in-memory) with MySQL support
- **Frontend**: Thymeleaf templates with Bootstrap 5
- **Security**: Spring Security
- **Charts**: Chart.js for data visualization
- **Build Tool**: Maven
- **Java Version**: 17

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. Clone or download the project
2. Navigate to the project directory
3. Run the application using Maven:

```bash
mvn spring-boot:run
```

4. The application will start on `http://localhost:8080/egg-depot`

### Access Points

- **Home Page**: http://localhost:8080/egg-depot/
- **Dashboard**: http://localhost:8080/egg-depot/dashboard
- **Products**: http://localhost:8080/egg-depot/products
- **Customers**: http://localhost:8080/egg-depot/customers
- **Orders**: http://localhost:8080/egg-depot/orders
- **Promotions**: http://localhost:8080/egg-depot/promotions
- **H2 Console**: http://localhost:8080/egg-depot/h2-console

### Database Configuration

The application uses H2 in-memory database by default. To connect to the H2 console:

- **JDBC URL**: `jdbc:h2:mem:eggdepot`
- **Username**: `sa`
- **Password**: `password`

## API Endpoints

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/available` - Get available products
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### Customers
- `GET /api/customers` - Get all customers
- `GET /api/customers/{id}` - Get customer by ID
- `GET /api/customers/email/{email}` - Get customer by email
- `POST /api/customers` - Create new customer
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer

### Orders
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/customer/{customerId}` - Get orders by customer
- `POST /api/orders` - Create new order
- `PATCH /api/orders/{id}/status` - Update order status
- `POST /api/orders/{id}/cancel` - Cancel order

### Promotions
- `GET /api/promotions` - Get all promotions
- `GET /api/promotions/active` - Get active promotions
- `GET /api/promotions/code/{code}` - Get promotion by code
- `POST /api/promotions` - Create new promotion
- `PUT /api/promotions/{id}` - Update promotion
- `DELETE /api/promotions/{id}` - Delete promotion

## Sample Data

The application automatically initializes with sample data on startup:

### Products
- Fresh Chicken Eggs (Large) - $4.99
- Organic Chicken Eggs (Medium) - $5.99
- Duck Eggs (Extra Large) - $7.99
- Quail Eggs (Small) - $3.49
- Free Range Chicken Eggs (Jumbo) - $6.99
- Goose Eggs (Large) - $9.99

### Customers
- 5 sample customers with complete profiles

### Promotions
- WELCOME10: 10% off first order
- FREESHIP: Free shipping on orders over $50
- EGGDEAL5: $5 off orders over $30

## Project Structure

```
src/
├── main/
│   ├── java/com/eggdepot/
│   │   ├── controller/     # REST controllers and web controllers
│   │   ├── model/         # JPA entities
│   │   ├── repository/    # Data access layer
│   │   ├── service/       # Business logic layer
│   │   ├── config/        # Configuration classes
│   │   └── EggDepotSalesApplication.java
│   └── resources/
│       ├── templates/     # Thymeleaf templates
│       ├── static/        # Static resources
│       └── application.properties
└── test/                  # Test classes
```

## Features in Detail

### Product Management
- Support for multiple egg types and sizes
- Real-time stock tracking
- Low stock alerts (products with < 10 units)
- Search and filter functionality
- Bulk operations support

### Order Processing
- Complete order lifecycle management
- Automatic stock updates on order placement
- Support for promotions and discounts
- Tax calculation (8% default)
- Order status tracking

### Marketing Tools
- Flexible promotion system
- Usage limits and tracking
- Customer-specific promotions
- Time-based campaigns
- Multiple discount types

### Analytics Dashboard
- Real-time sales metrics
- Revenue tracking
- Order status distribution
- Top-selling products
- Customer insights

## Development

### Adding New Features

1. **Models**: Add new entities in the `model` package
2. **Repositories**: Create data access interfaces in `repository` package
3. **Services**: Implement business logic in `service` package
4. **Controllers**: Add REST endpoints in `controller` package
5. **Frontend**: Update Thymeleaf templates in `templates` folder

### Database Schema

The application uses JPA/Hibernate for database operations. The schema includes:

- `products` - Product information
- `customers` - Customer data
- `orders` - Order details
- `order_items` - Order line items
- `promotions` - Marketing promotions

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is open source and available under the MIT License.

## Support

For support and questions, please open an issue in the repository or contact the development team.
