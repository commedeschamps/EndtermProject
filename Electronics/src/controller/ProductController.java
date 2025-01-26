package controller;

import util.ProductRepository;
import model.Product;

import java.util.List;

public class ProductController {
    private ProductRepository productRepository;

    public ProductController() {
        this.productRepository = new ProductRepository();
    }

    public void displayAllProducts() {
        List<Product> products = productRepository.getAllProducts();
        for (Product product : products) {
            System.out.println(product.getId() + " " + product.getName() + " - $" + product.getPrice() +
                    " (Quantity: " + product.getQuantity() + ") " + "Description: " + product.getDescription());
        }
    }

    public void addProduct(String name, String description, String category, double price, int quantity) {
        Product product = new Product(0, name, description, category, price, quantity);
        productRepository.addProduct(product);
        System.out.println("Product added successfully");
    }

    public void updateProduct(int id, String name, String description, String category, double price, int quantity) {
        Product product = new Product(id, name, description, category, price, quantity);
        productRepository.updateProduct(product);
        System.out.println("Product updated successfully");
    }

    public void deleteProduct(int id) {
        productRepository.deleteProduct(id);
        System.out.println("Product deleted successfully");
    }

    public void searchAndDisplayProducts(String name) {
        List<Product> products = productRepository.searchProductsByName(name); // Вызываем метод репозитория
        if (products.isEmpty()) {
            System.out.println("No products found with the given name.");
        } else {
            System.out.println("Search results:");
            for (Product product : products) {
                System.out.println(product.getId() + " " + product.getName() + " - $" + product.getPrice() +
                        " (Quantity: " + product.getQuantity() + ")" + " Description: " + product.getDescription());
            }
        }
    }
    public Product getProductById(int productId) {
        return productRepository.getProductById(productId);
    }
}
