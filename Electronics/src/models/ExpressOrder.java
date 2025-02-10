package models;

import java.util.Date;

public class ExpressOrder extends Order {
    public ExpressOrder(int userId, double totalAmount, double amount, Date orderDate, String shippingAddress, String billingAddress) {
        super(userId, totalAmount, orderDate, shippingAddress, billingAddress);
    }

    @Override
    public double calculateTotalPrice() {
        return totalAmount + 20; // Дополнительная плата за быструю доставку
    }
}