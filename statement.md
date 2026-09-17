# Project Statement

## Project Title

Retail Store Inventory and Billing Management System using Core Java

## Problem Statement

Small retail stores often manage product stock and billing manually. Manual records can make it difficult to keep stock quantities updated, calculate bills correctly, and review sales information.

This project provides a simple command-line application for managing products, tracking inventory, generating customer bills, storing sales history, and viewing basic store reports.

## Objectives

1. To manage retail store products and their stock quantities.
2. To simplify customer billing and total calculation.
3. To automatically update stock after a sale.
4. To store inventory and sales information using Java File I/O.
5. To generate basic inventory and sales reports.
6. To demonstrate practical use of Core Java concepts.

## Scope

The system is designed for a small retail store and provides product management, stock management, billing, sales history, and basic reporting through a command-line interface.

The system does not use a database, GUI, or external service.

## Target Users

- Small retail store owners
- Store staff
- Cashiers managing basic billing and stock

## Functional Requirements

1. Add a new product.
2. View all products and available stock.
3. Remove an existing product.
4. Restock an existing product.
5. Create a customer bill.
6. Calculate subtotal, discount, and final amount.
7. Reduce product stock automatically after a successful sale.
8. Store and display sales history.
9. Generate a basic store report.

## Non-Functional Requirements

- **Usability:** The application should have a simple menu-driven terminal interface.
- **Reliability:** Invalid input, missing products, and insufficient stock should be handled safely.
- **Maintainability:** The program should use separate classes and packages for different responsibilities.
- **Performance:** Common inventory and billing operations should complete quickly for a small store.
- **Error Handling:** Input and file-related errors should be handled using validation and exceptions.
- **Resource Efficiency:** The application should use lightweight text files and Java collections.

## High-Level Workflow

```text
Start
  ↓
Load inventory and sales history
  ↓
Display menu
  ↓
User selects an operation
  ↓
Perform inventory / billing / report operation
  ↓
Save updated data when required
  ↓
Return to menu
  ↓
Save & Exit
```

## Technologies Used

- Java 17+
- Java Collections
- Java File I/O
- Exception Handling
- Packages
- Object-Oriented Programming

## Project Limitations

- Command-line interface only.
- Designed for a small local store.
- Text-file persistence is used instead of a database.
- No authentication or multiple-user support.

## Future Enhancements

- Product search and update
- Tax/GST calculation
- Supplier management
- More detailed reports
- Database-backed storage
