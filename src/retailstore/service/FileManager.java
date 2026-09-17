package retailstore.service;

import retailstore.model.Bill;
import retailstore.model.Product;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class FileManager {
    private static final String INVENTORY_FILE = "inventory.txt";
    private static final String SALES_FILE = "sales.txt";

    public static void saveInventory(List<Product> products) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE))) {
            for (Product product : products) {
                writer.write(product.getId() + "|"
                        + product.getName() + "|"
                        + product.getCategory() + "|"
                        + product.getPrice() + "|"
                        + product.getQuantity());
                writer.newLine();
            }
        }
    }

    public static void loadInventory(InventoryManager inventory) throws IOException {
        File file = new File(INVENTORY_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split("\\|", -1);
                if (data.length != 5) {
                    continue;
                }

                try {
                    Product product = new Product(
                            Integer.parseInt(data[0]),
                            data[1],
                            data[2],
                            Double.parseDouble(data[3]),
                            Integer.parseInt(data[4]));
                    inventory.addProduct(product);
                } catch (IllegalArgumentException ignored) {
                    // Skip malformed record.
                }
            }
        }
    }

    public static void appendSale(Bill bill) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SALES_FILE, true))) {
            writer.write(bill.getBillNumber() + "|"
                    + safeText(bill.getCustomerName()) + "|"
                    + bill.getDateTime() + "|"
                    + bill.getSubtotal() + "|"
                    + bill.getDiscount() + "|"
                    + bill.getFinalTotal());
            writer.newLine();
        }
    }

    public static void loadSalesHistory(BillingManager billingManager) throws IOException {
        File file = new File(SALES_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split("\\|", -1);
                if (data.length != 6) {
                    continue;
                }

                try {
                    int billNumber = Integer.parseInt(data[0]);
                    String customerName = data[1];
                    LocalDateTime dateTime = LocalDateTime.parse(data[2]);
                    double subtotal = Double.parseDouble(data[3]);
                    double discount = Double.parseDouble(data[4]);
                    double finalTotal = Double.parseDouble(data[5]);

                    Bill bill = new Bill(billNumber, customerName, dateTime, subtotal, discount);
                    billingManager.addLoadedSale(bill);
                } catch (RuntimeException ignored) {
                    // Skip malformed record.
                }
            }
        }
    }

    private static String safeText(String text) {
        return text.replace("|", "/").replace("\n", " ").replace("\r", " ");
    }


}
