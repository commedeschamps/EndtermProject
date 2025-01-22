import java.util.List;
import java.util.ArrayList;
import controller.ProductController;
import model.User;
import User.UserRepository;
import java.util.Optional;
import java.util.Scanner;
import model.Order;
import Order.OrderItem;
import Order.OrderRepository;
import model.Cart;


public class Main {
    public static void main(String[] args) {
        ProductController productController = new ProductController();
        UserRepository userRepository = new UserRepository();
        Scanner scanner = new Scanner(System.in);
        OrderRepository orderRepository = new OrderRepository();
        Cart cart = new Cart();


        while (true) {
            System.out.println("\n -Electronics Store Management-");
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
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    productController.displayAllProducts();
                    break;

                case 2:
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();

                    System.out.print("Enter product description: ");
                    String description = scanner.nextLine();

                    System.out.print("Enter product category: ");
                    String category = scanner.nextLine();

                    System.out.print("Enter product price: ");
                    double price = scanner.nextDouble();

                    System.out.print("Enter product quantity: ");
                    int quantity = scanner.nextInt();

                    productController.addProduct(productName, description, category, price, quantity);
                    System.out.println("Product added successfully!");
                    break;


                case 3:
                    System.out.print("Enter product ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter new product name: ");
                    String newName = scanner.nextLine();

                    System.out.print("Enter new product description: ");
                    String newDescription = scanner.nextLine();

                    System.out.print("Enter new product category: ");
                    String newCategory = scanner.nextLine();

                    System.out.print("Enter new product price: ");
                    double newPrice = scanner.nextDouble();

                    System.out.print("Enter new product quantity: ");
                    int newQuantity = scanner.nextInt();

                    productController.updateProduct(updateId, newName, newDescription, newCategory, newPrice, newQuantity);
                    break;

                case 4:
                    System.out.print("Enter product ID to delete: ");
                    int deleteId = scanner.nextInt();
                    productController.deleteProduct(deleteId);
                    break;

                case 5:
                    System.out.println("- User Registration -");
                    System.out.print("Enter your name: ");
                    String userName = scanner.nextLine();

                    System.out.print("Enter your email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();

                    User newUser = new User(0, userName, email, password, "customer");
                    userRepository.registerUser(newUser);
                    System.out.println("User registered successfully!");
                    break;

                case 6:
                    System.out.println("- User Login -");
                    System.out.print("Enter your email: ");
                    String loginEmail = scanner.nextLine();

                    System.out.print("Enter your password: ");
                    String loginPassword = scanner.nextLine();

                    Optional<User> loggedInUser = userRepository.loginUser(loginEmail, loginPassword);
                    if (loggedInUser.isPresent()) {
                        System.out.println("Welcome, " + loggedInUser.get().getName() + "!");
                    } else {
                        System.out.println("Invalid email or password.");
                    }
                    break;

                case 7:
                    System.out.println("Exiting the program...");
                    return;

                case 8:
                    System.out.println("- Create Order -");
                    System.out.print("Enter your user ID: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();

                    List<OrderItem> orderItems = new ArrayList<>();
                    double totalPrice = 0.0;

                    while (true) {
                        System.out.print("Enter product ID to add to the order (or 0 to finish): ");
                        int productId = scanner.nextInt();
                        if (productId == 0) break;

                        System.out.print("Enter quantity: ");
                        int orderQuantity = scanner.nextInt();

                        System.out.print("Enter price per unit: ");
                        double unitPrice = scanner.nextDouble();

                        totalPrice += unitPrice * orderQuantity;
                        orderItems.add(new OrderItem(0, productId, orderQuantity, unitPrice));
                    }

                    String orderDate = java.time.LocalDate.now().toString();
                    Order newOrder = new Order(0, userId, orderDate, totalPrice, "Pending", orderItems);

                    int orderId = orderRepository.createOrder(newOrder);
                    if (orderId > 0) {
                        orderRepository.addOrderItems(orderId, orderItems);
                        System.out.println("Order created successfully with ID: " + orderId);
                    } else {
                        System.out.println("Failed to create order.");
                    }
                    break;
                case 9:
                    System.out.println("- Search Products -");
                    System.out.print("Enter product name to search: ");
                    String searchName = scanner.nextLine();

                    productController.searchAndDisplayProducts(searchName);
                    break;
                case 10:
                    System.out.println("- Your Cart -");
                    List<OrderItem> cartItems = cart.getItems();
                    if (cartItems.isEmpty()) {
                        System.out.println("Your cart is empty.");
                    } else {
                        for (OrderItem item : cartItems) {
                            System.out.println("Product ID: " + item.getProductId() +
                                    ", Quantity: " + item.getQuantity() +
                                    ", Price per unit: " + item.getPrice() +
                                    ", Total: " + (item.getPrice() * item.getQuantity()));
                        }
                        System.out.println("Total Price: $" + cart.calculateTotalPrice());
                    }
                    break;

                case 11:
                    System.out.print("Enter product ID to add to cart: ");
                    int addProductId = scanner.nextInt();
                    System.out.print("Enter quantity: ");
                    int addQuantity = scanner.nextInt();
                    System.out.print("Enter price per unit: ");
                    double addPrice = scanner.nextDouble();

                    cart.addItem(new OrderItem(0, addProductId, addQuantity, addPrice));
                    System.out.println("Product added to cart.");
                    break;

                case 12:
                    System.out.print("Enter product ID to remove from cart: ");
                    int removeProductId = scanner.nextInt();
                    cart.removeItem(removeProductId);
                    System.out.println("Product removed from cart.");
                    break;

                case 13:
                    if (cart.getItems().isEmpty()) {
                        System.out.println("Your cart is empty. Add items before checking out.");
                        break;
                    }

                    System.out.print("Enter your user ID to place the order: ");
                    int checkoutUserId = scanner.nextInt();

                    double totalOrderPrice = cart.calculateTotalPrice();
                    String checkoutDate = java.time.LocalDate.now().toString();

                    Order checkoutOrder = new Order(0, checkoutUserId, checkoutDate, totalOrderPrice, "Pending", cart.getItems());

                    int createdOrderId = orderRepository.createOrder(checkoutOrder);
                    if (createdOrderId > 0) {
                        orderRepository.addOrderItems(createdOrderId, cart.getItems());
                        System.out.println("Order placed successfully! Order ID: " + createdOrderId);
                        cart.clearCart();
                    } else {
                        System.out.println("Failed to place the order. Please try again.");
                    }
                    break;


                default:
                    System.out.println("Invalid choice.");



            }
        }
    }
}
