package models;

import java.util.Date;

public class InternationalOrder extends Order {
    public InternationalOrder(int userId, double totalAmount, double amount, Date orderDate, String shippingAddress, String billingAddress) {
        super(userId, totalAmount, orderDate, shippingAddress, billingAddress);
    }

    @Override
    public double calculateTotalPrice() {
        return totalAmount * 1.15; // Увеличение цены на 15% для международной доставки
    }
}