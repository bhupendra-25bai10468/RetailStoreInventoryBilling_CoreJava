package retailstore.model;

import retailstore.exception.InsufficientStockException;

public class Product {
    private int id;
    private String name;
    private String category;
    private double price;
    private int quantity;

    public Product(int id, String name, String category, double price, int quantity) {
        if (id <= 0 || price < 0 || quantity < 0) {
            throw new IllegalArgumentException("Invalid product details.");
        }
        this.id = id;
        this.name = validateText(name, "Product name");
        this.category = validateText(category, "Category");
        this.price = price;
        this.quantity = quantity;
    }

    private String validateText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " cannot be empty.");
        }
        return value.trim();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = validateText(name, "Product name");
    }

    public void setCategory(String category) {
        this.category = validateText(category, "Category");
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public void addStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Stock quantity must be positive.");
        }
        quantity += amount;
    }

    public void reduceStock(int amount) throws InsufficientStockException {
        if (amount <= 0 || amount > quantity) {
            throw new InsufficientStockException("Insufficient stock.");
        }
        quantity -= amount;
    }

    @Override
    public String toString() {
        return String.format("%-5d %-20s %-15s %10.2f %8d", id, name, category, price, quantity);
    }
}
