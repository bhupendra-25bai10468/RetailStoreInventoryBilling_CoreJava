# Design Diagrams

These diagrams are written in Mermaid so they can be viewed directly on GitHub or copied into a report.

## 1. System Architecture

```mermaid
flowchart TD
    A[User / Cashier] --> B[Main Menu - Main.java]
    B --> C[InventoryManager]
    B --> D[BillingManager]
    B --> E[ReportManager]
    C --> F[Product Model]
    D --> G[Bill Model]
    D --> H[BillItem Model]
    C --> I[FileManager]
    D --> I
    E --> C
    E --> D
    I --> J[inventory.txt]
    I --> K[sales.txt]
```

## 2. Workflow

```mermaid
flowchart TD
    A[Start Program] --> B[Load Inventory and Sales History]
    B --> C[Display Menu]
    C --> D{User Choice}
    D -->|View/Add/Remove/Restock| E[Update Inventory]
    D -->|Create Bill| F[Validate Products and Stock]
    F --> G[Calculate Total and Discount]
    G --> H[Reduce Stock]
    H --> I[Save Sale]
    D -->|Sales History| J[Display Sales]
    D -->|Store Report| K[Generate Report]
    E --> L[Return to Menu]
    I --> L
    J --> L
    K --> L
    L --> C
    D -->|Save & Exit| M[Save Data and Exit]
```

## 3. Use Case Diagram

```mermaid
flowchart LR
    U[Cashier / Store Staff]
    U --> A[View Inventory]
    U --> B[Add Product]
    U --> C[Remove Product]
    U --> D[Restock Product]
    U --> E[Create Bill]
    U --> F[View Sales History]
    U --> G[View Store Report]
    U --> H[Save and Exit]
```

## 4. Class Diagram

```mermaid
classDiagram
    class Main {
        +main(String[] args)
    }

    class Product {
        -int id
        -String name
        -String category
        -double price
        -int quantity
        +addStock(int)
        +reduceStock(int)
    }

    class BillItem {
        -Product product
        -int quantity
        +getTotal()
    }

    class Bill {
        -int billNumber
        -String customerName
        -LocalDateTime dateTime
        -List~BillItem~ items
        -double discount
        +addItem(BillItem)
        +getSubtotal()
        +getFinalTotal()
        +generateInvoice()
    }

    class InventoryManager {
        -List~Product~ products
        +addProduct(Product)
        +removeProduct(int)
        +restockProduct(int, int)
        +findProduct(int)
    }

    class BillingManager {
        -List~Bill~ salesHistory
        +createBill(...)
        +displaySalesHistory()
    }

    class FileManager {
        +saveInventory(...)
        +loadInventory(...)
        +appendSale(...)
        +loadSalesHistory(...)
    }

    class ReportManager {
        +displayReport(...)
    }

    class ProductNotFoundException
    class InsufficientStockException

    Main --> InventoryManager
    Main --> BillingManager
    Main --> ReportManager
    InventoryManager --> Product
    BillingManager --> Bill
    Bill --> BillItem
    BillItem --> Product
    FileManager --> InventoryManager
    FileManager --> BillingManager
    ReportManager --> InventoryManager
    ReportManager --> BillingManager
    BillingManager ..> ProductNotFoundException
    BillingManager ..> InsufficientStockException
```

## 5. Create Bill Sequence

```mermaid
sequenceDiagram
    actor User
    participant Main
    participant BillingManager
    participant InventoryManager
    participant FileManager

    User->>Main: Select Create Bill
    Main->>Main: Read customer and item details
    Main->>BillingManager: createBill(...)
    BillingManager->>InventoryManager: findProduct(id)
    InventoryManager-->>BillingManager: Product
    BillingManager->>BillingManager: Validate stock and calculate discount
    BillingManager->>InventoryManager: reduce stock
    BillingManager-->>Main: Bill
    Main->>FileManager: saveInventory(...)
    Main->>FileManager: appendSale(bill)
    Main-->>User: Display invoice
```
## 6. Storage Design

The application uses simple text files for local data persistence.

### inventory.txt

Each line stores:

ID | Product Name | Category | Price | Quantity

Example:
101|Rice 5kg|Grocery|320.0|20

### sales.txt

Each line stores:

Bill Number | Customer Name | Date/Time | Subtotal | Discount | Final Total

Example:
1001|Customer|2026-09-17T18:00|1000.0|0.0|1000.0
