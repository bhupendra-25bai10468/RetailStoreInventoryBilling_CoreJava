package retailstore.service;

import retailstore.exception.InsufficientStockException;
import retailstore.exception.ProductNotFoundException;
import retailstore.model.Bill;
import retailstore.model.BillItem;
import retailstore.model.Product;

import java.util.ArrayList;
import java.util.List;

public class BillingManager {
    private final List<Bill> salesHistory = new ArrayList<>();

    public Bill createBill(InventoryManager inventory, String customerName,
                           List<Integer> productIds, List<Integer> quantities)
            throws ProductNotFoundException, InsufficientStockException {

        if (productIds.size() != quantities.size() || productIds.isEmpty()) {
            throw new IllegalArgumentException("Invalid billing details.");
        }

        // Validate everything before changing stock.
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
            Product product = inventory.findProduct(productIds.get(i));
            if (product == null) {
                throw new ProductNotFoundException("Product with ID " + productIds.get(i) + " not found.");
            }
            if (quantities.get(i) <= 0) {
                throw new IllegalArgumentException("Quantity must be positive.");
            }
            if (product.getQuantity() < quantities.get(i)) {
                throw new InsufficientStockException(
                        "Insufficient stock for " + product.getName() + ". Available: " + product.getQuantity());
            }
            products.add(product);
        }

        Bill bill = new Bill(customerName);

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int quantity = quantities.get(i);
            bill.addItem(new BillItem(product, quantity));
        }

        double subtotal = bill.getSubtotal();
        double discountRate = 0;
        if (subtotal >= 5000) {
            discountRate = 0.10;
        } else if (subtotal >= 2000) {
            discountRate = 0.05;
        }
        bill.setDiscount(subtotal * discountRate);

        for (int i = 0; i < products.size(); i++) {
            products.get(i).reduceStock(quantities.get(i));
        }

        salesHistory.add(bill);
        return bill;
    }

    public List<Bill> getSalesHistory() {
        return salesHistory;
    }

    public void addLoadedSale(Bill bill) {
        salesHistory.add(bill);
    }

    public void displaySalesHistory() {
        if (salesHistory.isEmpty()) {
            System.out.println("No sales recorded.");
            return;
        }

        System.out.println("\n============================== SALES HISTORY ==============================");
        System.out.printf("%-8s %-20s %-18s %12s%n", "Bill No", "Customer", "Date/Time", "Final Total");
        System.out.println("---------------------------------------------------------------------------");
        for (Bill bill : salesHistory) {
            System.out.printf("%-8d %-20s %-18s %12.2f%n",
                    bill.getBillNumber(), bill.getCustomerName(), bill.getDateTime(), bill.getFinalTotal());
        }
    }
}
