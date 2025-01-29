package controllers;

import controllers.interfaces.IProductController;
import models.Product;
import repositories.interfaces.IProductRepository;

import java.util.List;

public class ProductController implements IProductController {
    private final IProductRepository repo;

    public ProductController(IProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createProduct(String name, String description, double price, int quantity, String category) {
        Product product = new Product(name, description, price, quantity, category);
        boolean created = repo.createProduct(product);
        return (created) ? "Product was created" : "Product creation failed";
    }

    @Override
    public String getProductById(int id) {
        Product product = repo.getProductById(id);
        return (product == null) ? "Product not found" : product.toString();
    }

    @Override
    public String getAllProducts() {
        List<Product> products = repo.getAllProducts();
        StringBuilder response = new StringBuilder();
        for (Product product : products) {
            response.append(product.toString()).append("\n");
        }
        return response.toString();
    }
}

