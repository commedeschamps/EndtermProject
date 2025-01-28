package User;

import model.User;
import model.Order;
import java.util.*;

public class UserRepository {
    private final List<User> users = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>(); // List of orders
    private int nextId = 1;

    public void registerUser(User user) {
        user.setId(nextId++);
        users.add(user);
    }

    public Optional<User> loginUser(String email, String password) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst();
    }

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUserId() == userId) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    public void updateUserBalance(int userId, double newBalance) {
        User user = getUserById(userId);
        if (user != null) {
            user.setBalance(newBalance);
        } else {
            System.out.println("User not found!");
        }
    }

    public Optional<User> getUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                System.out.println("User updated successfully!");
                return;
            }
        }
        System.out.println("User not found!");
    }

    public Optional<Order> getOrderById(int orderId) {
        return orders.stream()
                .filter(order -> order.getId() == orderId)
                .findFirst();
    }

    public void updateOrder(Order order) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == order.getId()) {
                orders.set(i, order);
                System.out.println("Order updated successfully!");
                return;
            }
        }
        System.out.println("Order not found!");
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    private User getUserById(int userId) {
        return users.stream()
                .filter(user -> user.getId() == userId)
                .findFirst()
                .orElse(null);
    }
}
