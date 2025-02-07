package controllers;

import controllers.interfaces.IProductController;
import models.Product;
import repositories.interfaces.IProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ProductController implements IProductController {
    private final IProductRepository repo;

    public ProductController(IProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createProduct(String name, String description, double price, int quantity, String category) {
        if (name == null || name.isEmpty()) {
            return "Invalid product name. It cannot be empty.";
        }
        if (description == null || description.isEmpty()) {
            return "Invalid product description. It cannot be empty.";
        }
        if (category == null || category.isEmpty()) {
            return "Invalid product category. It cannot be empty.";
        }
        if (price <= 0) {
            return "Invalid product price. It must be greater than 0.";
        }
        if (quantity < 0) {
            return "Invalid product quantity. It cannot be negative.";
        }

        Product product = new Product(0, name, description, price, quantity, category);
        return repo.createProduct(product) ? "Product was created successfully." : "Product creation failed. Please try again.";
    }

    @Override
    public String getProductById(int id) {
        if (id <= 0) {
            return "Invalid product ID. It must be a positive number.";
        }

        Product product = repo.getProductById(id);
        return (product != null) ? product.toString() : "Product not found.";
    }

    @Override
    public String getAllProducts() {
        List<Product> products = repo.getAllProducts();
        if (products == null || products.isEmpty()) {
            return "No products available.";
        }

        return products.stream()
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String getProductsByCategory(String category) {
        if (category == null || category.isEmpty()) {
            return "Invalid category input. It cannot be empty.";
        }

        List<Product> products = repo.getAllProducts().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        if (products.isEmpty()) {
            return "No products found in this category.";
        }

        return products.stream()
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String getProductsByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0) {
            return "Invalid price range. Prices cannot be negative.";
        }
        if (minPrice > maxPrice) {
            return "Invalid price range. Minimum price cannot be greater than maximum price.";
        }

        List<Product> products = repo.getAllProducts().stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());

        if (products.isEmpty()) {
            return "No products found in this price range.";
        }

        return products.stream()
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String sortProductsByPriceAscending() {
        List<Product> products = repo.getAllProducts();
        if (products == null || products.isEmpty()) {
            return "No products available.";
        }

        return products.stream()
                .sorted((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()))
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String sortProductsByPriceDescending() {
        List<Product> products = repo.getAllProducts();
        if (products == null || products.isEmpty()) {
            return "No products available.";
        }

        return products.stream()
                .sorted((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()))
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
    }
}
