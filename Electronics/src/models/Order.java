package models;

import java.util.Date; 

public class Order {
    private int id;
    private int userId;
    private double totalAmount;
    private Date orderDate;
    private String shippingAddress;
    private String billingAddress;

    public Order(int userId, double totalAmount, Date orderDate, String shippingAddress, String billingAddress) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
    }

    public Order(int id, int userId, Date orderDate, double totalAmount) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public Order(int userId, double totalAmount, String orderDate, String shippingAddress, String billingAddress) {
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                '}';
    }
}
