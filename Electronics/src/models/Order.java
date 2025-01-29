package models;

import java.util.Date;

public class Order {
    private int id;
    private int userId;
    private Date orderDate;
    private double totalAmount;
    private String deliveryMethod; // choice delivery or pick-up
    private String paymentMethod;  //Cash, credit card, nfc

    public Order(int userId, Date orderDate, double totalAmount, String deliveryMethod, String paymentMethod) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.deliveryMethod = deliveryMethod;
        this.paymentMethod = paymentMethod;
    }

    public Order(int id, int userId, Date orderDate, double totalAmount) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.deliveryMethod = deliveryMethod;
        this.paymentMethod = paymentMethod;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public String toString() {
        return "Order ID: " + id +
                "\nUser ID: " + userId +
                "\nOrder Date: " + orderDate +
                "\nTotal Amount: " + totalAmount +
                "\nDelivery Method: " + deliveryMethod +
                "\nPayment Method: " + paymentMethod;
    }
}
