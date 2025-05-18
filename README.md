<div align="center">
  <img src="https://via.placeholder.com/150x150/31355B/F8C1E1?text=LMICF" alt="LOOK MOM I CAN FLY Logo" width="150" height="150">
  <h1 style="font-size: 3em; color: #31355B; text-transform: uppercase; margin-bottom: 0;">LOOK MOM I CAN FLY</h1>
  <p style="font-size: 1.2em; color: #755540; margin-top: 0.5em;">An Online Marketplace for Sneakers and Hype Products.</p>
</div>

<p style="font-size: 1.1em; text-align: center; margin: 2em 0;">
  A senior thesis project that simulates the operations of platforms like StockX, with a heavy focus on backend architecture and clean code practices.
</p>

<div class="create-ask-container" style="max-width: 1200px; margin: 70px auto 0 auto; padding: 20px; min-height: 530px;">

## 🧠 Project Overview

"LOOK MOM I CAN FLY" is a full-stack e-commerce platform built to showcase advanced programming skills, particularly in backend development. This project emulates the mechanics of a real-time resale marketplace, enabling users to buy and sell high-demand products such as sneakers and streetwear.

## ⚙️ Backend

The core strength of this project lies in its robust backend, developed with a modern, scalable, and maintainable architecture using:

- **Spring Boot (Java 21)** — A high-performance backend framework used to build RESTful APIs, manage business logic, and handle all server-side operations.
- **Flyway** — Manages all version-controlled SQL database migrations, ensuring consistency.
- **Gradle** — Used as the project's build tool, optimized for performance and continuous delivery.
- **Spring Security + JWT** — Implements secure user authentication, role-based access control, and token-based session handling.
- **Stripe API** — Integrates secure payment handling.
- **Twilio API** — Sends SMS notifications for real-time transaction updates.
- **Thymeleaf** — Used to send dynamic and styled HTML emails.
- **SonarQube** — All backend code passed Sonar's Quality Gates, ensuring code quality, maintainability, and zero critical bugs.

✅ This backend follows industry standards and embraces clean code principles. Extensive attention was given to architecture, security, and maintainability.

## 🔁 CI/CD & DevOps

- **GitHub Actions** — Configured for continuous integration and to enforce quality checks before allowing merges to the main branch.
- **Version Control** — Git used rigorously throughout the project with structured commits and pull request reviews.

## 💻 Frontend

- **Angular 19 (TypeScript)** — Provides a modern SPA built on RxJS for reactive programming using observables.
- A lightweight JavaScript script was developed to dynamically update the status of active orders and sales.

## 🗄️ Database

Relational SQL database — Structured for transactional integrity and optimized query performance.

## 📌 Why This Project?

This project is the culmination of my software developing degree and was built to demonstrate my ability to architect, develop, and maintain clean and intelligent backend and frontend systems.

</div>

<style>
  .create-ask-container {
    max-width: 1200px;
    margin: 70px auto 0 auto;
    padding: 20px;
    min-height: 530px;
  }
  
  .product-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
  }
  
  .product-header h1 {
    font-size: 24px;
    font-weight: 600;
    color: #212121;
    text-transform: uppercase;
    margin: 0;
  }
  
  .size-label {
    font-size: 16px;
    color: #755540;
    font-weight: 500;
  }
  
  .ask-content {
    display: flex;
    gap: 40px;
  }
  
  .product-image-container {
    margin-top: 20px;
    margin-left: 60px;
    flex: 0 0 55%; 
    max-width: 60%; 
    margin-right: 5%; 
    padding-left: 0;
  }
  
  .product-image {
    width: 100%;
    height: auto;
    object-fit: contain;
    max-height: 500px; 
    margin-left: -10%; 
  }
  
  .ask-form-container {
    flex: 1;
    max-width: 500px;
    margin-top: -10px;
  }
  
  .form-group {
    margin-bottom: 10px;
  }
  
  .form-group label {
    display: block;
    font-size: 14px;
    color: #755540;
    margin-bottom: 8px;
    font-weight: 500;
    text-transform: uppercase;
  }
  
  .form-group input,
  .form-group select {
    width: 100%;
    padding: 12px 15px;
    border: 1px solid #f5f5f5;
    border-radius: 4px;
    font-size: 16px;
    transition: border-color 0.3s;
  }
  
  .form-group input:focus,
  .form-group select:focus {
    outline: none;
    border-color: #755540;
  }
  
  .price-info {
    margin: 5px 0;
    padding: 8px;
    background-color: #f5f5f5;
    border-radius: 4px;
  }
  
  .price-info-row {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
  }
  
  .price-info-row:last-child {
    margin-bottom: 0;
  }
  
  .user-info {
    margin: 20px 0;
  }
  
  .info-row {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
    padding-bottom: 15px;
    border-bottom: 1px solid #f5f5f5;
  }
  
  .info-row:last-child {
    border-bottom: none;
    margin-bottom: 0;
    padding-bottom: 0;
  }
  
  .payout-breakdown {
    margin: 15px 0;
    border-top: 1px solid #f5f5f5;
    padding-top: 15px;
  }
  
  .breakdown-row {
    display: flex;
    justify-content: space-between;
    margin-bottom: 12px;
  }
  
  .breakdown-row.total {
    font-weight: 600;
    margin-top: 20px;
    padding-top: 10px;
    border-top: 1px solid #f5f5f5;
  }
  
  .form-actions {
    margin-top: 30px;
  }
  
  .create-button {
    width: 100%;
    padding: 15px;
    background-color: #31355B;
    color: #F8C1E1;
    border: none;
    border-radius: 4px;
    font-size: 16px;
    font-weight: 500;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  .create-button:hover {
    background-color: #414675;
  }
  
  .create-button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
  }
  
  .error-message {
    color: #ED1C24;
    font-size: 14px;
    margin-top: 15px;
    padding: 10px;
    background-color: #f5f5f5;
    border-radius: 4px;
    display: block; 
  }
  
  .error-message.show {
    display: block;
  }
  
  .loading-spinner {
    text-align: center;
    padding: 50px;
    font-size: 18px;
    color: #755540;
  }
  
  @media (max-width: 768px) {
    .ask-content {
      flex-direction: column;
    }
    
    .product-image-container,
    .ask-form-container {
      max-width: 100%;
    }
  }
</style>
