package models;

import java.util.Date;

public class Order {
    private int id;
    private int userId;
    private Date orderDate;
    private double totalPrice;

    public Order() {
    }

    public Order(int userId, Date orderDate, double totalPrice) {
        setUserId(userId);
        setOrderDate(orderDate);
        setTotalPrice(totalPrice);
    }

    public Order(int id, int userId, Date orderDate, double totalPrice) {
        this(userId, orderDate, totalPrice);
        setId(id);
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalPrice +
                '}';
    }
}
