# HoaTuoiUIT — Backend

REST API and business logic for the HoaTuoiUIT e-commerce platform.

The backend provides APIs for customer accounts, products, flowers, categories, cart, orders, promotions, reviews, blog content, payments, file uploads, password recovery, and the AI recommendation chatbot.

## Overview

The backend is built using Spring Boot with a layered architecture.

```text
Client / Admin
      ↓
 REST Controllers
      ↓
    DTOs
      ↓
   Services
      ↓
 Repositories
      ↓
 PostgreSQL
```

The project also contains dedicated security, mapping, exception-handling, and utility layers.

## Tech Stack

* Java 21
* Spring Boot 3.4.5
* Spring Web
* Spring Data JPA
* Spring Security
* JWT
* PostgreSQL
* Maven
* JavaMailSender

## Architecture

The source code is organized into separate layers:

```text
src/main/java/com/example/backendhoatuoiuit/

├── config/
├── controller/
├── dto/
├── entity/
├── exception/
├── mapper/
├── repository/
├── security/
├── service/
├── utils/
└── BackendhoatuoiuitApplication.java
```

### Controller Layer

Exposes REST endpoints for the application's main modules.

### Service Layer

Contains application and business logic such as:

* Cart processing
* Order creation
* Promotion calculation
* Order status transitions
* Review management
* Password recovery
* Chatbot processing
* Payment-method management

### Repository Layer

Uses Spring Data JPA repositories for database access.

### DTO / Mapper Layers

DTOs are used for API data transfer, while mapper classes handle conversions between application objects and DTOs.

## Main Modules

### Authentication & Authorization

The application uses Spring Security with JWT-based authentication.

Security features include:

* JWT token generation and validation
* Stateless authentication
* JWT authentication filter
* Role-based authorization
* Protected administrative endpoints

The security layer includes:

```text
JwtAuthenticationFilter
JwtTokenProvider
SecurityConfig
```

Method-level authorization is also used for protected operations.

## Customer Management

Provides APIs for customer-related operations, including customer information and account functionality.

## Product & Catalog Management

The backend provides APIs for:

* Products
* Flowers
* Categories
* Occasions
* Promotions

These modules support the product catalog used by the customer frontend and admin dashboard.

## Cart

Cart APIs support operations such as:

* Managing cart items
* Updating quantities
* Applying product-related discounts
* Creating orders from the cart

## Orders

The order module contains the main order workflow.

Orders can be created from:

* Customer cart
* Direct product purchase

The order service handles:

* Order creation
* Order products
* Promotion discounts
* Total calculation
* Order status transitions
* Order cancellation
* Order confirmation
* Shipping status
* Delivery status

The order status workflow includes states such as:

```text
PENDING
   ↓
PROCESSING
   ↓
SHIPPED
   ↓
DELIVERED
```

Orders can also be cancelled where the corresponding status rules allow it.

## Reviews

The backend provides APIs and services for product reviews.

The review module contains:

```text
Review
ReviewDTO
ReviewMapper
ReviewRepository
ReviewService
ReviewController
```

## Blog

The backend provides APIs for blog-related content and blog post management.

## Payment Methods

The backend contains a payment-method management module.

It provides CRUD operations for payment methods and their active status.

```text
GET    /api/payments
GET    /api/payments/{id}
POST   /api/payments
PUT    /api/payments/{id}
DELETE /api/payments/{id}
```

The current backend should be understood as **payment-method management**, rather than a completed end-to-end payment gateway integration.

## Password Recovery

The password recovery flow uses a verification code sent by email.

The flow includes:

```text
Customer email
      ↓
Generate verification code
      ↓
Store verification code
      ↓
Send email
      ↓
Verify code
      ↓
Password confirmation
```

The implementation uses `JavaMailSender` for email delivery.

## AI Flower Recommendation Chatbot

The backend includes an AI-powered flower recommendation chatbot using the OpenRouter API.

The flow is:

```text
User request
     ↓
OpenRouter API
     ↓
AI recommendation
     ↓
Flower matching
     ↓
Active catalog products
     ↓
Product information / links
```

The service:

1. Sends the user's request to the OpenRouter API.
2. Receives the AI recommendation.
3. Matches suggested flower types with active flowers in the database.
4. Finds active products associated with those flowers.
5. Returns product information and links.

## File Upload

The backend contains file-upload functionality used by the application for uploaded media.

## Analytics

The order module provides data for administrative analytics, including:

* Daily revenue
* Daily order count
* Revenue within a date range
* Order count within a date range

These APIs are consumed by the Admin dashboard.

## Database

The application uses PostgreSQL with Spring Data JPA.

The repository also contains the database SQL file and database-related resources.

## API Documentation

A Postman resource is linked from the repository for API testing and documentation.

## Related Repositories

- **Frontend:** https://github.com/UPIN-0583/FrontEndHoaTuoiUIT
- **Admin:** https://github.com/UPIN-0583/admin-hoatuoituit

## Demo

[Watch Demo](https://drive.google.com/file/d/1GaBvQiyyy_MdYWSE1nWZoCXcR6ClVaqo/view)

## Project Structure

```text
backendhoatuoiuit/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/backendhoatuoiuit/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── entity/
│   │   │       ├── exception/
│   │   │       ├── mapper/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       ├── service/
│   │   │       └── utils/
│   │   └── resources/
│   └── test/
├── Dockerfile
├── pom.xml
└── hoatuoiuit.sql
```

## Notes

This repository contains the backend REST API and business logic. The customer-facing UI and administrative dashboard are maintained separately in the related repositories.
