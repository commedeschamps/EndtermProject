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
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        if (userId > 0) {  
            this.userId = userId;
        } else {
            throw new IllegalArgumentException("User ID must be positive.");
        }
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        if (items != null) {  
            this.items = items;
        } else {
            throw new IllegalArgumentException("Items list cannot be null.");
        }
    }

    public void addItem(OrderItem item) {
        if (item != null) {
            items.add(item);
        } else {
            throw new IllegalArgumentException("Cannot add null item.");
        }
    }

    public void removeItem(OrderItem item) {
        if (item != null && items.contains(item)) {
            items.remove(item);
        } else {
            throw new IllegalArgumentException("Item does not exist in the cart.");
        }
    }

    public void clear() {
        items.clear();
    }

    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userId=" + userId +
                ", items=" + items +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}
