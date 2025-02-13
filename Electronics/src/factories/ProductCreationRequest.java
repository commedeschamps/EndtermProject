package models;

public class ProductCreationRequest {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String category;
    private String manufacturer;

    public ProductCreationRequest(Integer id, String name, String description, Double price, Integer quantity, String category, String manufacturer) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.manufacturer = manufacturer;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getManufacturer() {
        return manufacturer;
    }
}