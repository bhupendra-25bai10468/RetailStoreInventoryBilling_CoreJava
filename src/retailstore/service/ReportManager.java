package retailstore.service;

import retailstore.model.Bill;
import retailstore.model.Product;

import java.util.List;

public class ReportManager {
    public void displayReport(InventoryManager inventory, BillingManager billingManager) {
        List<Product> products = inventory.getProducts();
        List<Bill> sales = billingManager.getSalesHistory();

        int totalStock = 0;
        int lowStock = 0;
        double totalSales = 0;

        for (Product product : products) {
            totalStock += product.getQuantity();
            if (product.getQuantity() <= 5) {
                lowStock++;
            }
        }

        for (Bill bill : sales) {
            totalSales += bill.getFinalTotal();
        }

        System.out.println("\n============================== STORE REPORT ==============================");
        System.out.println("Total Products     : " + products.size());
        System.out.println("Total Units in Stock: " + totalStock);
        System.out.println("Low Stock Products : " + lowStock);
        System.out.printf("Total Sales        : %.2f%n", totalSales);
        System.out.println("Total Bills        : " + sales.size());
        System.out.println("=========================================================================");
    }
}
