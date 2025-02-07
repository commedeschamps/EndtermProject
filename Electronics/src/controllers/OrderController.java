package controllers;

import controllers.interfaces.IOrderController;
import models.Order;
import repositories.interfaces.IOrderRepository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderController implements IOrderController {
    private final IOrderRepository repo;

    public OrderController(IOrderRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createOrder(int userId, double totalAmount) {
        if (userId <= 0) {
            return "Invalid user ID. It must be a positive number.";
        }
        if (totalAmount <= 0) {
            return "Invalid total amount. It must be a positive number.";
        }

        Order newOrder = new Order(userId, totalAmount, Date.valueOf(LocalDateTime.now().toLocalDate()),
                "123 Shipping St.", "123 Billing St.");
        return repo.createOrder(newOrder) ? "Order was created" : "Order creation failed";
    }

    @Override
    public String getOrderById(int id) {
        if (id <= 0) {
            return "Invalid order ID. It must be a positive number.";
        }

        Order order = repo.getOrderById(id);
        return (order != null) ? order.toString() : "Order not found";
    }

    @Override
    public String getAllOrders() {
        List<Order> orders = repo.getAllOrders();
        if (orders == null || orders.isEmpty()) {
            return "No orders available.";
        }

        return orders.stream()
                .map(Order::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String getUserOrders(int userId) {
        if (userId <= 0) {
            return "Invalid user ID. It must be a positive number.";
        }

        List<Order> orders = repo.getOrdersByUserId(userId);
        if (orders == null || orders.isEmpty()) {
            return "No orders found for this user.";
        }

        return orders.stream()
                .map(Order::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String getOrderDetailsById(int orderId) {
        if (orderId <= 0) {
            return "Invalid order ID. It must be a positive number.";
        }

        String orderDetails = repo.getOrderDetailsById(orderId);
        return (orderDetails != null && !orderDetails.isEmpty()) ? orderDetails : "Order details not found.";
    }
}
