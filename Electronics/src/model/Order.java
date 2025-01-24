package model;

import java.util.List;


public class Order {
    private int id;
    private int userId;
    private double totalPrice;
    private String orderDate;
    private String status;
    private List<OrderItem> orderItems;

    public Order(int id, int userId, String orderDate, double totalPrice, String status, List<OrderItem> orderItems) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderItems = orderItems;
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
}
