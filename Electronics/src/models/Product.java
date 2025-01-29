package models;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String category;

    public Product(String name, String description, double price, int quantityб, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', description='" + description + "', price=" + price + ", quantity=" + quantity + ", category='" + category + "'}";
    }
}

