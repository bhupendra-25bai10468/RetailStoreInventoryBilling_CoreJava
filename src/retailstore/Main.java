package retailstore;

import retailstore.exception.InsufficientStockException;
import retailstore.exception.ProductNotFoundException;
import retailstore.model.Bill;
import retailstore.model.Product;
import retailstore.service.BillingManager;
import retailstore.service.FileManager;
import retailstore.service.InventoryManager;
import retailstore.service.ReportManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InventoryManager inventory = new InventoryManager();
    private static final BillingManager billingManager = new BillingManager();
    private static final ReportManager reportManager = new ReportManager();

    public static void main(String[] args) {
        try {
            FileManager.loadInventory(inventory);
            FileManager.loadSalesHistory(billingManager);

            if (inventory.getProducts().isEmpty()) {
                inventory.addSampleProducts();
                FileManager.saveInventory(inventory.getProducts());
                System.out.println("Sample products added to the new inventory.");
            }

            runMenu();
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void runMenu() {
        int choice;

        do {
            showMenu();
            choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1 -> inventory.displayInventory();
                    case 2 -> addProduct();
                    case 3 -> removeProduct();
                    case 4 -> restockProduct();
                    case 5 -> createBill();
                    case 6 -> billingManager.displaySalesHistory();
                    case 7 -> reportManager.displayReport(inventory, billingManager);
                    case 8 -> saveAndExit();
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (ProductNotFoundException | InsufficientStockException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("File error: " + e.getMessage());
            }
        } while (choice != 8);
    }

    private static void showMenu() {
        System.out.println("\n========================================");
        System.out.println("   RETAIL STORE INVENTORY & BILLING");
        System.out.println("========================================");
        System.out.println("1. View Inventory");
        System.out.println("2. Add Product");
        System.out.println("3. Remove Product");
        System.out.println("4. Restock Product");
        System.out.println("5. Create Bill");
        System.out.println("6. Sales History");
        System.out.println("7. Store Report");
        System.out.println("8. Save & Exit");
    }

    private static void addProduct() throws IOException {
        int id = readInt("Enter product ID: ");
        String name = readText("Enter product name: ");
        String category = readText("Enter category: ");
        double price = readDouble("Enter price: ");
        int quantity = readInt("Enter quantity: ");

        inventory.addProduct(new Product(id, name, category, price, quantity));
        FileManager.saveInventory(inventory.getProducts());
        System.out.println("Product added successfully.");
    }

    private static void removeProduct() throws ProductNotFoundException, IOException {
        int id = readInt("Enter product ID to remove: ");
        inventory.removeProduct(id);
        FileManager.saveInventory(inventory.getProducts());
        System.out.println("Product removed successfully.");
    }

    private static void restockProduct() throws ProductNotFoundException, IOException {
        int id = readInt("Enter product ID: ");
        Product product = inventory.findProduct(id);

        if (product == null) {
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }

        int amount = readInt("Enter quantity to add: ");
        product.addStock(amount);
        FileManager.saveInventory(inventory.getProducts());
        System.out.println("Stock updated successfully.");
    }

    private static void createBill()
            throws ProductNotFoundException, InsufficientStockException, IOException {
        String customerName = readText("Enter customer name: ");
        int numberOfItems = readInt("Enter number of different products: ");

        if (numberOfItems <= 0) {
            throw new IllegalArgumentException("Number of items must be positive.");
        }

        List<Integer> productIds = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        for (int i = 0; i < numberOfItems; i++) {
            int productId = readInt("Enter product ID for item " + (i + 1) + ": ");
            int quantity = readInt("Enter quantity: ");

            if (productIds.contains(productId)) {
                throw new IllegalArgumentException("Do not enter the same product twice in one bill.");
            }

            productIds.add(productId);
            quantities.add(quantity);
        }

        Bill bill = billingManager.createBill(inventory, customerName, productIds, quantities);
        FileManager.saveInventory(inventory.getProducts());
        FileManager.appendSale(bill);

        System.out.println(bill.generateInvoice());
    }

    private static void saveAndExit() throws IOException {
        FileManager.saveInventory(inventory.getProducts());
        System.out.println("All data saved successfully.");
        System.out.println("Thank you for using the Retail Store System.");
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty.");
        }
    }
}
