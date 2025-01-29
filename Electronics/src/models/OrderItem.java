package models;

public class OrderItem {
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double price;

    public OrderItem() {
    }

    public OrderItem(int orderId, int productId, int quantity, double price) {
        setOrderId(orderId);
        setProductId(productId);
        setQuantity(quantity);
        setPrice(price);
    }

    public OrderItem(int id, int orderId, int productId, int quantity, double price) {
        this(orderId, productId, quantity, price);
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
