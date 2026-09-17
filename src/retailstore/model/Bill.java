package retailstore.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Bill {
    private static int nextBillNumber = 1001;

    private final int billNumber;
    private final String customerName;
    private final LocalDateTime dateTime;
    private final List<BillItem> items;
    private double discount;
    private Double loadedSubtotal;

    public Bill(String customerName) {
        this(nextBillNumber++, customerName, LocalDateTime.now());
    }

    public Bill(int billNumber, String customerName, LocalDateTime dateTime,
                double subtotal, double discount) {
        if (billNumber <= 0) {
            throw new IllegalArgumentException("Invalid bill number.");
        }
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }
        this.billNumber = billNumber;
        this.customerName = customerName.trim();
        this.dateTime = dateTime;
        this.items = new ArrayList<>();
        this.loadedSubtotal = subtotal;
        this.discount = discount;
        nextBillNumber = Math.max(nextBillNumber, billNumber + 1);
    }

    private Bill(int billNumber, String customerName, LocalDateTime dateTime) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }
        this.billNumber = billNumber;
        this.customerName = customerName.trim();
        this.dateTime = dateTime;
        this.items = new ArrayList<>();
    }

    public void addItem(BillItem item) {
        items.add(item);
        loadedSubtotal = null;
    }

    public int getBillNumber() {
        return billNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<BillItem> getItems() {
        return items;
    }

    public double getSubtotal() {
        if (loadedSubtotal != null) {
            return loadedSubtotal;
        }

        double total = 0;
        for (BillItem item : items) {
            total += item.getTotal();
        }
        return total;
    }

    public void setDiscount(double discount) {
        if (discount < 0 || discount > getSubtotal()) {
            throw new IllegalArgumentException("Invalid discount.");
        }
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }

    public double getFinalTotal() {
        return getSubtotal() - discount;
    }

    public String generateInvoice() {
        StringBuilder invoice = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        invoice.append("\n==============================================\n");
        invoice.append("                 RETAIL STORE                \n");
        invoice.append("                 SALES BILL                  \n");
        invoice.append("==============================================\n");
        invoice.append("Bill No   : ").append(billNumber).append("\n");
        invoice.append("Customer  : ").append(customerName).append("\n");
        invoice.append("Date      : ").append(dateTime.format(formatter)).append("\n");
        invoice.append("----------------------------------------------\n");
        invoice.append(String.format("%-18s %5s %10s\n", "Product", "Qty", "Amount"));
        invoice.append("----------------------------------------------\n");

        for (BillItem item : items) {
            invoice.append(String.format("%-18s %5d %10.2f\n",
                    item.getProduct().getName(), item.getQuantity(), item.getTotal()));
        }

        invoice.append("----------------------------------------------\n");
        invoice.append(String.format("Subtotal              : %10.2f\n", getSubtotal()));
        invoice.append(String.format("Discount              : %10.2f\n", discount));
        invoice.append(String.format("Final Total           : %10.2f\n", getFinalTotal()));
        invoice.append("==============================================\n");
        invoice.append("             Thank you! Visit again.         \n");
        invoice.append("==============================================\n");

        return invoice.toString();
    }
}
