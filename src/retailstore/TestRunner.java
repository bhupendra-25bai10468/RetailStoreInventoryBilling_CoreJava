package retailstore;

import retailstore.exception.InsufficientStockException;
import retailstore.exception.ProductNotFoundException;
import retailstore.model.Product;
import retailstore.service.InventoryManager;

public class TestRunner {
    private static int passed = 0;
    private static int total = 0;

    public static void main(String[] args) {
        System.out.println("--- RUNNING AUTOMATED UNIT TESTS ---");

        testAddAndGetProduct();
        testRestockProduct();
        testInsufficientStockException();
        testProductNotFoundException();

        System.out.printf("%nResult: %d/%d tests passed.%n", passed, total);
        if (passed == total) {
            System.out.println("ALL TESTS PASSED SUCCESSFULLY!");
        }
    }

    private static void assertTrue(String testName, boolean condition) {
        total++;
        if (condition) {
            System.out.println("[PASS] " + testName);
            passed++;
        } else {
            System.out.println("[FAIL] " + testName);
        }
    }

    private static void testAddAndGetProduct() {
        InventoryManager inv = new InventoryManager();
        inv.addProduct(new Product(999, "Test Item", "Test", 50.0, 10));
        Product p = inv.findProduct(999);
        assertTrue("Add and retrieve product", p != null && p.getPrice() == 50.0);
    }

    private static void testRestockProduct() {
        InventoryManager inv = new InventoryManager();
        inv.addProduct(new Product(998, "Test Milk", "Dairy", 30.0, 5));
        inv.restockProduct(998, 10);
        assertTrue("Restock increases quantity", inv.findProduct(998).getQuantity() == 15);
    }

    private static void testInsufficientStockException() {
        Product p = new Product(997, "Test Bread", "Bakery", 20.0, 2);
        boolean caught = false;
        try {
            p.reduceStock(10); // Trying to take 10 when only 2 exist
        } catch (InsufficientStockException e) {
            caught = true;
        }
        assertTrue("Catches InsufficientStockException on oversell", caught);
    }

    private static void testProductNotFoundException() {
        InventoryManager inv = new InventoryManager();
        Product p = inv.findProduct(123456); // Does not exist
        assertTrue("Returns null for missing product", p == null);
    }
}
