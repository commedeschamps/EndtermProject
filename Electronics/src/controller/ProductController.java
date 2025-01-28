package controller;

import java.time.LocalDateTime;
import java.util.*;
import model.*;
import util.ProductRepository;

public class ProductController {
    private final ProductRepository productRepository = new ProductRepository();
    private final Cart cart = new Cart();

    // Add a new product to the repository
    public void addProduct(String name, String description, String category, double price, int quantity) {
        Product product = new Product(0, name, description, category, price, quantity);
        productRepository.addProduct(product);
        System.out.println("Product added successfully!");
    }

    // Update an existing product
    public void updateProduct(int id, String name, String description, String category, double price, int quantity) {
        Product product = new Product(id, name, description, category, price, quantity);
        productRepository.updateProduct(product);
        System.out.println("Product updated successfully!");
    }

    // Delete a product from the repository
    public void deleteProduct(int id) {
        productRepository.deleteProduct(id);
        System.out.println("Product deleted successfully!");
    }

    // Get a product by its ID
    public Product getProductById(int id) {
        return productRepository.getProductById(id);
    }

    // Add product to the shopping cart
    public void addProductToCart(int productId, int quantity) {
        Product product = productRepository.getProductById(productId);
        if (product == null) {
            System.out.println("Invalid product ID.");
            return;
        }

        if (product.getQuantity() < quantity) {
            System.out.println("Not enough stock available for product: " + product.getName());
            return;
        }

        cart.addItem(new OrderItem(0, productId, quantity, product.getPrice()));

        int newQuantity = product.getQuantity() - quantity;
        productRepository.updateProductQuantity(productId, newQuantity);

        System.out.println("Product added to cart!");
    }

    // Remove product from the shopping cart
    public void removeProductFromCart(int productId) {
        cart.removeItem(productId);
        System.out.println("Product removed from cart.");
    }

    // Display all items in the cart
    public void displayCartItems() {
        List<OrderItem> items = cart.getItems();
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Cart items:");
            items.forEach(item -> {
                Product product = productRepository.getProductById(item.getProductId());
                System.out.println("Product ID: " + item.getProductId() + ", Name: " + (product != null ? product.getName() : "Unknown") +
                        ", Quantity: " + item.getQuantity() + ", Price per unit: $" + item.getPrice() +
                        ", Total: $" + (item.getPrice() * item.getQuantity()));
            });
            System.out.println("Total price: $" + cart.calculateTotalPrice());
        }
    }

    // Create an order from the cart
    public void createOrder(int userId) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Cannot create order.");
            return;
        }

        double totalPrice = cart.calculateTotalPrice();
        System.out.println("Total Price: $" + totalPrice);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose delivery method (1 for Pickup, 2 for Delivery):");
        int deliveryChoice = scanner.nextInt();
        scanner.nextLine();

        if (deliveryChoice == 1) {
            System.out.println("Thank you for your order! Please pick it up at the store.");
        } else if (deliveryChoice == 2) {
            System.out.println("Enter your delivery address:");
            String address = scanner.nextLine();
            System.out.println("Thank you for your order! Your items will be delivered to: " + address + " tomorrow. Courier will contact you before delivery.");
        } else {
            System.out.println("Invalid delivery method. Order not completed.");
            return;
        }

        Order order = new Order(
                0,
                userId,
                LocalDateTime.now().toString(),
                totalPrice,
                "Pending",
                deliveryChoice == 1 ? "Pickup" : "Delivery",
                cart.getItems()
        );

        System.out.println("Order created successfully!");
        cart.clearCart();
    }

    // Checkout the cart and update user balance
    public void checkout(int userId, double userBalance) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Cannot checkout.");
            return;
        }

        double totalPrice = cart.calculateTotalPrice();
        if (userBalance < totalPrice) {
            System.out.println("Insufficient balance to complete the checkout.");
            System.out.println("Total price: $" + totalPrice + ", Your balance: $" + userBalance);
            return;
        }

        System.out.println("Checkout successful! Total price: $" + totalPrice);
        System.out.println("Your new balance: $" + (userBalance - totalPrice));
        cart.clearCart();
    }
    public void displayAllProducts() {
        List<Product> products = productRepository.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            products.forEach(product -> {
                System.out.println("Product ID: " + product.getId() + ", Name: " + product.getName() +
                        ", Price: $" + product.getPrice() +
                        ", Description: " + product.getDescription() +
                        ", Category: " + product.getCategory() +
                        ", Quantity: " + product.getQuantity());
            });
        }
    }

    public void sortProductsByPriceAscending() {
    }

    public void sortProductsByPriceDescending() {
    }

    public void displayAllProductsBrief() {
    }

    public void searchAndDisplayProducts(String searchName) {
    }

    public void updateProductQuantity(int productId, int remainingQuantity) {
    }

    public void displayProductsByCategory(String category) {
    }

    public void displayAllCategories() {
    }
}
