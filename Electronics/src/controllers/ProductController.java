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
        if (List.of(name, description, category).stream().anyMatch(s -> s == null || s.isEmpty()) || price <= 0 || quantity < 0) {
            return "Invalid input values. Please check the data and try again.";
        }

        Product product = new Product(0, name, description, price, quantity, category);
        return repo.createProduct(product) ? "Product was created successfully." : "Product creation failed. Please try again.";
    }

    @Override
    public String getProductById(int id) {
        return (id <= 0) ? "Invalid product ID." :
                repo.getProductById(id) != null ? repo.getProductById(id).toString() : "Product not found.";
    }

    @Override
    public String getAllProducts() {
        return repo.getAllProducts().stream()
                .map(Product::toString)
                .collect(Collectors.collectingAndThen(Collectors.joining("\n"),
                        result -> result.isEmpty() ? "No products available." : result));
    }

    @Override
    public String getProductsByCategory(String category) {
        if (category == null || category.isEmpty()) {
            return "Invalid category input.";
        }

        return repo.getAllProducts().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .map(Product::toString)
                .collect(Collectors.collectingAndThen(Collectors.joining("\n"),
                        result -> result.isEmpty() ? "No products found in this category." : result));
    }

    @Override
    public String getProductsByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
            return "Invalid price range.";
        }

        return repo.getAllProducts().stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .map(Product::toString)
                .collect(Collectors.collectingAndThen(Collectors.joining("\n"),
                        result -> result.isEmpty() ? "No products found in this price range." : result));
    }

    @Override
    public String sortProductsByPriceAscending() {
        return repo.getAllProducts().stream()
                .sorted((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()))
                .map(Product::toString)
                .collect(Collectors.collectingAndThen(Collectors.joining("\n"),
                        result -> result.isEmpty() ? "No products available." : result));
    }

    @Override
    public String sortProductsByPriceDescending() {
        return repo.getAllProducts().stream()
                .sorted((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()))
                .map(Product::toString)
                .collect(Collectors.collectingAndThen(Collectors.joining("\n"),
                        result -> result.isEmpty() ? "No products available." : result));
    }
}
