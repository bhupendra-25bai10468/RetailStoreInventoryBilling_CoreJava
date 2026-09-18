package retailstore.service;

import retailstore.exception.ProductNotFoundException;
import retailstore.model.Product;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (findProduct(product.getId()) != null) {
            throw new IllegalArgumentException("Product ID already exists.");
        }
        products.add(product);
    }

    public void removeProduct(int id) throws ProductNotFoundException {
        Product product = findProduct(id);
        if (product == null) {
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
        products.remove(product);
    }

    public void restockProduct(int id, int amount) throws ProductNotFoundException {
    Product product = findProduct(id);

    if (product == null) {
        throw new ProductNotFoundException(
                "Product with ID " + id + " not found."
        );
    }

    product.addStock(amount);
    }
    
    public Product findProduct(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void displayInventory() {
        if (products.isEmpty()) {
            System.out.println("No products in inventory.");
            return;
        }

        System.out.println("\n============================== INVENTORY ==============================");
        System.out.printf("%-5s %-20s %-15s %10s %8s%n", "ID", "Name", "Category", "Price", "Stock");
        System.out.println("------------------------------------------------------------------------");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public void addSampleProducts() {
        addProduct(new Product(101, "Rice 5kg", "Grocery", 320.00, 20));
        addProduct(new Product(102, "Cooking Oil", "Grocery", 180.00, 15));
        addProduct(new Product(103, "Bath Soap", "Personal Care", 45.00, 30));
        addProduct(new Product(104, "Toothpaste", "Personal Care", 90.00, 18));
        addProduct(new Product(105, "Shampoo", "Personal Care", 150.00, 12));
    }
}
