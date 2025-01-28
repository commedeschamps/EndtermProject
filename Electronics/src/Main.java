import java.util.*;
import controller.ProductController;
import model.*;
import User.UserRepository;

public class Main {
    private static final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
    private static final ProductController productController = new ProductController();
    private static final UserRepository userRepository = new UserRepository();
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
                case 15 -> productController.sortProductsByPriceAscending();
                case 16 -> productController.sortProductsByPriceDescending();
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
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

    private static void addProduct() {
        String name = getUserStringInput("Enter product name: ");
        String description = getUserStringInput("Enter product description: ");
        String category = getUserStringInput("Enter product category: ");
        double price = getUserDoubleInput("Enter product price: ");
        int quantity = getUserIntInput("Enter product quantity: ");
        productController.addProduct(name, description, category, price, quantity);
    }

    private static void updateProduct() {
        int id = getUserIntInput("Enter product ID to update: ");
        String name = getUserStringInput("Enter new product name: ");
        String description = getUserStringInput("Enter new product description: ");
        String category = getUserStringInput("Enter new product category: ");
        double price = getUserDoubleInput("Enter new product price: ");
        int quantity = getUserIntInput("Enter new product quantity: ");
        productController.updateProduct(id, name, description, category, price, quantity);
    }

    private static void deleteProduct() {
        int id = getUserIntInput("Enter product ID to delete: ");
        productController.deleteProduct(id);
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

    private static void searchProducts() {
        String searchName = getUserStringInput("Enter the product name to search: ");
        productController.searchAndDisplayProducts(searchName);
    }

    private static void viewCart() {
        productController.displayCartItems();
    }

    private static void addToCart() {
        productController.displayAllProducts();
        int productId = getUserIntInput("Enter product ID to add to cart: ");
        int quantity = getUserIntInput("Enter quantity: ");
        productController.addProductToCart(productId, quantity);
    }

    private static void removeFromCart() {
        productController.displayCartItems();
        int productId = getUserIntInput("Enter product ID to remove from cart: ");
        productController.removeProductFromCart(productId);
    }

    private static void createOrder() {
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }
        productController.createOrder(loggedInUser.getId());
    }

    private static void checkout() {
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }
        productController.checkout(loggedInUser.getId(), loggedInUser.getBalance());
    }

    private static void displayProductsByCategory() {
        productController.displayAllCategories();
        String category = getUserStringInput("Enter category: ");
        productController.displayProductsByCategory(category);
    }

    private static void exitProgram() {
        System.out.println("Exiting the program...");
        scanner.close();
        System.exit(0);
    }

    private static int getUserIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
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
        }
        double input = scanner.nextDouble();
        scanner.nextLine();
        return input;
    }
}
