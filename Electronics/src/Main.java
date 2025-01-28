import java.util.*;
import controller.ProductController;
import model.*;
import User.UserRepository;



public class Main {
    private static final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
    private static final ProductController productController = new ProductController();
    private static final UserRepository userRepository = new UserRepository();
    private static final Cart cart = new Cart();
    private static User loggedInUser = null;

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = getUserIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> productController.displayAllProducts();
                case 2 -> addProduct();
                case 3 -> updateProduct();
                case 4 -> deleteProduct();
                case 5 -> registerUser();
                case 6 -> loginUser();
                case 7 -> exitProgram();
                case 8 -> createOrder();
                case 9 -> searchProducts();
                case 10 -> viewCart();
                case 11 -> addToCart();
                case 12 -> removeFromCart();
                case 13 -> checkout();
                case 14 -> displayProductsByCategory();
                case 15 -> sortProductsByPriceAscending();
                case 16 -> sortProductsByPriceDescending();
                case 17 -> viewProductDetails();
                case 18 -> updateUserDetails();
                case 19 -> viewAllOrders();
                case 20 -> cancelOrder();
                case 21 -> addFunds();
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void exitProgram() {
        System.out.println("Exiting the program...");
        scanner.close();
        System.exit(0);
    }

    private static void showMenu() {
        System.out.println("\n-Electronics Store Management-");
        System.out.println("1. View All Products");
        System.out.println("2. Add New Product");
        System.out.println("3. Update Product");
        System.out.println("4. Delete Product");
        System.out.println("5. Register User");
        System.out.println("6. Login User");
        System.out.println("7. Exit");
        System.out.println("8. Create Order");
        System.out.println("9. Search Products");
        System.out.println("10. View Cart");
        System.out.println("11. Add to Cart");
        System.out.println("12. Remove from Cart");
        System.out.println("13. Checkout");
        System.out.println("14. View Products by Category");
        System.out.println("15. Sort Products by Price (Ascending)");
        System.out.println("16. Sort Products by Price (Descending)");
        System.out.println("17. View Product Details");
        System.out.println("18. Update User Details");
        System.out.println("19. View All Orders");
        System.out.println("20. Cancel Order");
        System.out.println("21. Add Funds to Account");
    }

    private static void sortProductsByPriceAscending() {
        productController.sortProductsByPriceAscending();
    }

    private static void sortProductsByPriceDescending() {
        productController.sortProductsByPriceDescending();
    }


    private static int getUserIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
            System.out.print(prompt);
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    private static String getUserStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static double getUserDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
            System.out.print(prompt);
        }
        double input = scanner.nextDouble();
        scanner.nextLine();
        return input;
    }

    private static void viewProductDetails() {
        productController.displayAllProductsBrief();

        int productId = getUserIntInput("Enter product ID to view details: ");
        Product product = productController.getProductById(productId);

        if (product == null) {
            System.out.println("Invalid product ID.");
            return;
        }

        System.out.println("Product Details:");
        System.out.println("Name: " + product.getName());
        System.out.println("Description: " + product.getDescription());
        System.out.println("Category: " + product.getCategory());
        System.out.println("Price: $" + product.getPrice());
        System.out.println("Quantity: " + product.getQuantity());
    }


    private static void updateUserDetails() {
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }

        String name = getUserStringInput("Enter your new name (current: " + loggedInUser.getName() + "): ");
        String email = getUserStringInput("Enter your new email (current: " + loggedInUser.getEmail() + "): ");
        if (!email.contains("@") || !email.contains(".")) {
            System.out.println("Invalid email format.");
            return;
        }

        String password = getUserStringInput("Enter your new password: ");
        loggedInUser.setName(name);
        loggedInUser.setEmail(email);
        loggedInUser.setPassword(password);

        userRepository.updateUser(loggedInUser);
        System.out.println("User details updated successfully!");
    }

    private static void viewAllOrders() {
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }

        List<Order> orders = userRepository.getOrdersByUserId(loggedInUser.getId());
        if (orders.isEmpty()) {
            System.out.println("You have no past orders.");
            return;
        }

        System.out.println("Your Orders:");
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getId());
            System.out.println("Date: " + order.getOrderDate());
            System.out.println("Status: " + order.getStatus());
            System.out.println("Total Price: $" + order.getTotalPrice());
            System.out.println("Delivery Method: " + order.getDeliveryMethod());
            System.out.println("Payment Method: " + order.getPaymentMethod());  // Display payment method
            System.out.println("Items: ");
            for (OrderItem item : order.getOrderItems()) {
                Product product = productController.getProductById(item.getProductId());
                System.out.println("  Product: " + product.getName() + ", Quantity: " + item.getQuantity());
            }
            System.out.println();
        }
    }




    private static void cancelOrder() {
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }

        int orderId = getUserIntInput("Enter order ID to cancel: ");
        Optional<Order> orderOpt = userRepository.getOrderById(orderId);

        if (orderOpt.isEmpty()) {
            System.out.println("Order not found.");
            return;
        }

        Order order = orderOpt.get();
        if (!order.getStatus().equals("Pending")) {
            System.out.println("Order cannot be canceled because it's already processed.");
            return;
        }

        order.setStatus("Canceled");
        userRepository.updateOrder(order);
        System.out.println("Order canceled successfully.");
    }

    private static void addFunds() {
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }

        double amount = getUserDoubleInput("Enter amount to add to your account: ");
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }

        loggedInUser.setBalance(loggedInUser.getBalance() + amount);
        userRepository.updateUser(loggedInUser);
        System.out.println("Funds added successfully! New balance: $" + loggedInUser.getBalance());
    }

    private static void addProduct() {
        String name = getUserStringInput("Enter product name: ");
        String description = getUserStringInput("Enter product description: ");
        String category = getUserStringInput("Enter product category: ");
        double price = getUserDoubleInput("Enter product price: ");
        int quantity = getUserIntInput("Enter product quantity: ");

        productController.addProduct(name, description, category, price, quantity);
        System.out.println("Product added successfully!");
    }

    private static void updateProduct() {
        int id = getUserIntInput("Enter product ID to update: ");
        String name = getUserStringInput("Enter new product name: ");
        String description = getUserStringInput("Enter new product description: ");
        String category = getUserStringInput("Enter new product category: ");
        double price = getUserDoubleInput("Enter new product price: ");
        int quantity = getUserIntInput("Enter new product quantity: ");

        productController.updateProduct(id, name, description, category, price, quantity);
        System.out.println("Product updated successfully!");
    }

    private static void deleteProduct() {
        int id = getUserIntInput("Enter product ID to delete: ");
        productController.deleteProduct(id);
        System.out.println("Product deleted successfully!");
    }

    private static void registerUser() {
        String name = getUserStringInput("Enter your name: ");
        String email = getUserStringInput("Enter your email: ");

        if (!email.contains("@") || !email.contains(".")) {
            System.out.println("Invalid email format. Please try again.");
            return;
        }

        if (userRepository.getUserByEmail(email).isPresent()) {
            System.out.println("An account with this email already exists. Please log in.");
            return;
        }

        String password = getUserStringInput("Enter your password: ");
        double initialBalance = getUserDoubleInput("Enter your initial balance: ");

        User newUser = new User(0, name, email, password, "customer", initialBalance);
        userRepository.registerUser(newUser);
        System.out.println("User registered successfully!");
    }


    private static void loginUser() {
        String email = getUserStringInput("Enter your email: ");
        String password = getUserStringInput("Enter your password: ");

        Optional<User> user = userRepository.loginUser(email, password);
        if (user.isPresent()) {
            loggedInUser = user.get();
            System.out.println("Welcome, " + loggedInUser.getName() + "!");
        } else {
            System.out.println("Invalid email or password.");
        }
    }


    private static void createOrder() {
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }

        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0;

        while (true) {
            int productId = getUserIntInput("Enter product ID to add to the order (or 0 to finish): ");
            if (productId == 0) break;

            int quantity = getUserIntInput("Enter quantity: ");
            Product product = productController.getProductById(productId);

            if (product == null) {
                System.out.println("Invalid product ID. Try again.");
                continue;
            }
            if (quantity > product.getQuantity()) {
                System.out.println("Not enough stock for product: " + product.getName() +
                        ". Available quantity: " + product.getQuantity());
                continue;
            }

            double unitPrice = product.getPrice();
            totalPrice += unitPrice * quantity;
            orderItems.add(new OrderItem(0, productId, quantity, unitPrice));
        }

        // Ask for the delivery method
        String deliveryMethod = getUserStringInput("Enter delivery method (self-pickup or delivery): ");
        while (!deliveryMethod.equalsIgnoreCase("self-pickup") && !deliveryMethod.equalsIgnoreCase("delivery")) {
            System.out.println("Invalid choice. Please enter either 'self-pickup' or 'delivery'.");
            deliveryMethod = getUserStringInput("Enter delivery method (self-pickup or delivery): ");
        }

        // Ask for the payment method
        String paymentMethod = getUserStringInput("Enter payment method (credit card, PayPal, cash): ");
        while (!paymentMethod.equalsIgnoreCase("credit card") && !paymentMethod.equalsIgnoreCase("paypal") && !paymentMethod.equalsIgnoreCase("cash")) {
            System.out.println("Invalid choice. Please enter either 'credit card', 'paypal', or 'cash'.");
            paymentMethod = getUserStringInput("Enter payment method (credit card, PayPal, cash): ");
        }

        // Create the order with the selected delivery and payment method
        Order newOrder = new Order(0, loggedInUser.getId(), java.time.LocalDate.now().toString(), totalPrice,
                "Pending", orderItems, deliveryMethod, paymentMethod);
        System.out.println("Order created successfully! Delivery method: " + deliveryMethod + ", Payment method: " + paymentMethod);
    }



    private static void searchProducts() {
        String searchName = getUserStringInput("Enter the product name to search: ");
        productController.searchAndDisplayProducts(searchName);
    }

    private static void viewCart() {
        if (cart.getItems().isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            cart.getItems().forEach(item -> {
                System.out.println("Product ID: " + item.getProductId() + ", Quantity: " + item.getQuantity() +
                        ", Price: $" + item.getPrice() + ", Total: $" + (item.getPrice() * item.getQuantity()));
            });
            System.out.println("Total price: $" + cart.calculateTotalPrice());
        }
    }

    private static void addToCart() {
        // Show all products first
        productController.displayAllProducts();

        int productId = getUserIntInput("Enter product ID to add to cart: ");
        int quantity = getUserIntInput("Enter quantity: ");
        Product product = productController.getProductById(productId);

        if (product == null) {
            System.out.println("Invalid product ID.");
            return;
        }

        if (quantity > product.getQuantity()) {
            System.out.println("Not enough stock for product: " + product.getName() +
                    ". Available quantity: " + product.getQuantity());
            return;
        }

        cart.addItem(new OrderItem(0, productId, quantity, product.getPrice()));
        System.out.println("Item added to cart: " + product.getName());
    }

    private static void removeFromCart() {
        if (cart.getItems().isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("Items in your cart:");
        cart.getItems().forEach(item -> {
            Product product = productController.getProductById(item.getProductId());
            System.out.println("Product ID: " + item.getProductId() + ", Name: " + product.getName() +
                    ", Quantity: " + item.getQuantity() + ", Price: $" + item.getPrice() + ", Total: $" +
                    (item.getPrice() * item.getQuantity()));
        });

        int productId = getUserIntInput("Enter product ID to remove from cart: ");
        cart.removeItem(productId);
        System.out.println("Item removed from cart.");
    }


    private static void checkout() {
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }

        if (cart.getItems().isEmpty()) {
            System.out.println("Your cart is empty. Add items before checking out.");
            return;
        }

        double totalPrice = cart.calculateTotalPrice();

        if (loggedInUser.getBalance() < totalPrice) {
            System.out.println("Insufficient balance. Total cost: $" + totalPrice +
                    ". Your balance: $" + loggedInUser.getBalance());
            return;
        }

        loggedInUser.setBalance(loggedInUser.getBalance() - totalPrice);

        for (OrderItem item : cart.getItems()) {
            Product product = productController.getProductById(item.getProductId());
            int remainingQuantity = product.getQuantity() - item.getQuantity();
            productController.updateProductQuantity(item.getProductId(), remainingQuantity);
        }

        cart.clearCart();

        System.out.println("Checkout successful! Total cost: $" + totalPrice);
        System.out.println("Your remaining balance: $" + loggedInUser.getBalance());
    }

    private static void displayProductsByCategory() {
        System.out.println("\nDisplaying all categories:");
        productController.displayAllCategories();

        String category = getUserStringInput("Enter the product category to display: ");
        productController.displayProductsByCategory(category);
    }

}

