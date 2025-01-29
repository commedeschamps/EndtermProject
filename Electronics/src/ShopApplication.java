import controllers.interfaces.*;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class ShopApplication {
    private final IUserController userController;
    private final IProductController productController;
    private final IOrderController orderController;
    private final ICartController cartController;
    private final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

    public ShopApplication(IUserController userController, IProductController productController,
                           IOrderController orderController, IOrderItemController orderItemController, ICartController cartController) {
        this.userController = userController;
        this.productController = productController;
        this.orderController = orderController;
        this.cartController = cartController;
    }

    public void start() {
        while (true) {
            mainMenu();
            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1 -> viewAllProducts();
                    case 2 -> viewProductDetails();
                    case 3 -> createUser();
                    case 4 -> loginUser();
                    case 5 -> createOrder();
                    case 6 -> viewCart();
                    case 7 -> addToCart();
                    case 8 -> removeFromCart();
                    case 9 -> checkout();
                    case 10 -> addProduct();
                    case 0 -> exitApplication();
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
            System.out.println("----------------------------------------");
        }
    }

    private void mainMenu() {
        System.out.println("\n--- Welcome to the Electronics Store ---");
        System.out.println("1. View All Products");
        System.out.println("2. View Product Details");
        System.out.println("3. Register User");
        System.out.println("4. Login User");
        System.out.println("5. Create Order");
        System.out.println("6. View Cart");
        System.out.println("7. Add to Cart");
        System.out.println("8. Remove from Cart");
        System.out.println("9. Checkout");
        System.out.println("10. Add New Product");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    private void viewAllProducts() {
        System.out.println("All Products:");
        String response = productController.getAllProducts();
        System.out.println(response);
    }

    private void viewProductDetails() {
        int productId = getIntInput("Enter Product ID to view details: ");
        String response = productController.getProductById(productId);
        System.out.println(response);
    }

    private void createUser() {
        System.out.println("Register New User:");
        String name = getStringInput("Enter name: ");
        String surname = getStringInput("Enter surname: ");
        String gender = getStringInput("Enter gender (male/female): ");
        String response = userController.createUser(name, surname, gender);
        System.out.println(response);
    }

    private void loginUser() {
        System.out.println("Login functionality is not implemented yet.");
    }

    private void createOrder() {
        System.out.println("Create a New Order:");
        int userId = getIntInput("Enter User ID: ");
        double totalPrice = getDoubleInput("Enter Total Price: ");
        String response = orderController.createOrder(userId, totalPrice);
        System.out.println(response);
    }

    private void viewCart() {
        int userId = getIntInput("Enter User ID to view cart: ");
        String response = cartController.getCartByUserId(userId);
        System.out.println(response);
    }

    private void addToCart() {
        int userId = getIntInput("Enter User ID: ");
        int productId = getIntInput("Enter Product ID: ");
        int quantity = getIntInput("Enter Quantity: ");
        double price = getDoubleInput("Enter Price: ");
        String response = cartController.addItemToCart(userId, productId, quantity, price);
        System.out.println(response);
    }

    private void removeFromCart() {
        System.out.println("Remove From Cart functionality is not implemented yet.");
    }

    private void checkout() {
        System.out.println("Checkout functionality is not implemented yet.");
    }

    private void exitApplication() {
        System.out.println("Exiting application...");
        scanner.close();
        System.exit(0);
    }

    private int getIntInput(String prompt) {
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

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private double getDoubleInput(String prompt) {
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

    private void addProduct() {
        String name = getStringInput("Enter product name: ");
        String description = getStringInput("Enter product description: ");
        double price = getDoubleInput("Enter product price: ");
        int quantity = getIntInput("Enter product quantity: ");
        String category = getStringInput("Enter product category: ");

        String response = productController.createProduct(name, description, price, quantity, category);
        System.out.println(response);
    }
}
