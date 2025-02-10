package models;

import java.util.Date;

public class RegularOrder extends Order {
    public RegularOrder(int userId, double totalAmount, double amount, Date orderDate, String shippingAddress, String billingAddress) {
        super(userId, totalAmount, orderDate, shippingAddress, billingAddress);
    }

    @Override
    public double calculateTotalPrice() {
        return totalAmount;
    }
}