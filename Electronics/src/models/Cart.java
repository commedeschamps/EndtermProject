package models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int userId;
    private List<OrderItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public Cart(int userId) {
        this();
        setUserId(userId);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userId=" + userId +
                ", items=" + items +
                '}';
    }
}
