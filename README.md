# Retail Store Inventory & Billing Management System using Core Java

## Overview

A simple command-line retail store management system built using Core Java. The application manages products, tracks stock, creates customer bills, stores sales history, and generates a basic store report.

## Features

- View inventory
- Add products
- Remove products
- Restock products
- Create bills
- Automatic stock reduction after a sale
- Simple discount calculation
- Sales history
- Store report
- File-based data persistence
- Input validation and exception handling

## Technologies Used

- Java 17 or later
- Java Collections (`ArrayList`)
- Java File I/O (`FileReader`, `FileWriter`, `BufferedReader`, `BufferedWriter`)
- Java `LocalDateTime`
- Custom exceptions

No MySQL, SQLite, JDBC, JPA, GUI, Maven, or external libraries are required.

## Project Structure

```text
RetailStoreInventoryBilling_CoreJava/
├── src/
│   └── retailstore/
│       ├── Main.java
│       ├── model/
│       │   ├── Product.java
│       │   ├── Bill.java
│       │   └── BillItem.java
│       ├── service/
│       │   ├── InventoryManager.java
│       │   ├── BillingManager.java
│       │   ├── FileManager.java
│       │   └── ReportManager.java
│       └── exception/
│           ├── ProductNotFoundException.java
│           └── InsufficientStockException.java
├── docs/
│   └── design.md
├── README.md
├── statement.md
└── .gitignore
```

## Requirements

- JDK 17 or later
- Command Prompt, PowerShell, or another terminal

Check Java:

```text
java -version
javac -version
```

## Compile and Run

### Windows PowerShell

From the project root:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -d out (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName })
java -cp out retailstore.Main
```

### Linux/macOS

From the project root:

```bash
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out retailstore.Main
```

## Application Menu

```text
1. View Inventory
2. Add Product
3. Remove Product
4. Restock Product
5. Create Bill
6. Sales History
7. Store Report
8. Save & Exit
```

## Data Files

The program automatically creates these files in the project root:

- `inventory.txt` - stores product and stock data
- `sales.txt` - stores sales summary records

These generated files are ignored by Git and are recreated when the program runs.

## Discount Rules

- Subtotal below ₹2,000: no discount
- Subtotal from ₹2,000 to ₹4,999.99: 5% discount
- Subtotal ₹5,000 or more: 10% discount

## Testing

### Automated Unit Testing
Run the automated test suite from PowerShell:

```powershell
javac -d out (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName })
java -cp out retailstore.TestRunner
```

### Automated Grader Demo Mode
Run the non-interactive validation:

```powershell
java -cp out retailstore.Main --demo
```

Manual test cases:

| Test | Expected Result |
|---|---|
| Add a valid product | Product added and inventory saved |
| Add duplicate product ID | Error message shown |
| Remove existing product | Product removed |
| Remove missing product | `ProductNotFoundException` handled |
| Restock existing product | Stock quantity increases |
| Restock missing product | Error message shown |
| Create bill with sufficient stock | Bill generated and stock reduced |
| Create bill with insufficient stock | `InsufficientStockException` handled |
| Enter invalid numeric input | Program asks for valid input |
| Restart program | Saved inventory and sales history are loaded |
| Generate report | Product, stock, low-stock and sales totals shown |

## Limitations

- This is a small command-line application intended for a single local store.
- Data is stored in text files instead of a database.
- There is no user login or role management.
- Sales history stores summary information rather than complete item-level bill reconstruction after restart.

## Future Enhancements

- Product search and update
- GST/tax support
- Supplier management
- Daily/monthly reports
- Database support in a future version

## Learning Outcomes Demonstrated

The project demonstrates Java classes and objects, constructors, encapsulation, collections, packages, exception handling, custom exceptions, file I/O, strings, loops, conditions, and `LocalDateTime`.
