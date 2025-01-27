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

        Order newOrder = new Order(0, loggedInUser.getId(), java.time.LocalDate.now().toString(), totalPrice, "Pending", orderItems);
        System.out.println("Order created successfully!");
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
        // Show all items in the cart first
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

