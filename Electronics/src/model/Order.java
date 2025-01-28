package model;

import java.util.List;

public class Order {
    private int id;
    private int userId;
    private String orderDate;
    private double totalPrice;
    private String status;
    private List<OrderItem> orderItems;
    private String deliveryMethod;  // Delivery method (self-pickup or delivery)
    private String paymentMethod;   // New field for payment method

    // Constructor with payment method and delivery method
    public Order(int id, int userId, String orderDate, double totalPrice, String status,
                 List<OrderItem> orderItems, String deliveryMethod, String paymentMethod) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderItems = orderItems;
        this.deliveryMethod = deliveryMethod;
        this.paymentMethod = paymentMethod; // Set payment method
    }

    public Order(int id, int userId, String string, double totalPrice, String pending, String s, List<OrderItem> items) {
    }

    // Getter and setter for delivery method
    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    // Getter and setter for payment method
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItem> getItems() {
        return orderItems;
    }
}
