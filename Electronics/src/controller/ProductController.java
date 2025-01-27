package controller;

import java.util.*;
import model.Product;
import util.ProductRepository;

public class ProductController {
    private final ProductRepository productRepository = new ProductRepository();

    public void addProduct(String name, String description, String category, double price, int quantity) {
        Product product = new Product(0, name, description, category, price, quantity);
        productRepository.addProduct(product);
    }

    public void updateProduct(int id, String name, String description, String category, double price, int quantity) {
        Product product = new Product(id, name, description, category, price, quantity);
        productRepository.updateProduct(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteProduct(id);
    }

    public Product getProductById(int id) {
        return productRepository.getProductById(id);
    }

    public void searchAndDisplayProducts(String name) {
        List<Product> products = productRepository.searchProductsByName(name);
        if (products.isEmpty()) {
            System.out.println("No products found with the name: " + name);
        } else {
            System.out.println("Found products:");
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    public void displayAllCategories() {
        List<String> categories = productRepository.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("No categories available.");
        } else {
            System.out.println("Available categories:");
            for (String category : categories) {
                System.out.println(" - " + category);
            }
        }
    }

    public void displayProductsByCategory(String category) {
        List<Product> products = productRepository.getProductsByCategory(category);
        if (products.isEmpty()) {
            System.out.println("No products found in the category: " + category);
        } else {
            System.out.println("Products in the category " + category + ":");
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    public void updateProductQuantity(int productId, int remainingQuantity) {
        productRepository.updateProductQuantity(productId, remainingQuantity);
    }

    // Display all products
    public void displayAllProducts() {
        List<Product> products = productRepository.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            products.forEach(product -> {
                System.out.println("Product ID: " + product.getId() + ", Name: " + product.getName() +
                        ", Price: $" + product.getPrice());
            });
        }
    }

    // Sort products by price in ascending order
    public void sortProductsByPriceAscending() {
        List<Product> products = productRepository.getAllProducts();
        products.sort(Comparator.comparingDouble(Product::getPrice));
        System.out.println("Products sorted by price (ascending):");
        products.forEach(product -> {
            System.out.println("Product ID: " + product.getId() + ", Name: " + product.getName() +
                    ", Price: $" + product.getPrice());
        });
    }

    // Sort products by price in descending order
    public void sortProductsByPriceDescending() {
        List<Product> products = productRepository.getAllProducts();
        products.sort((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
        System.out.println("Products sorted by price (descending):");
        products.forEach(product -> {
            System.out.println("Product ID: " + product.getId() + ", Name: " + product.getName() +
                    ", Price: $" + product.getPrice());
        });
    }
}
