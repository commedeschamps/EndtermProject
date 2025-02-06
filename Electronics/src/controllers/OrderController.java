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
        Order newOrder = new Order(userId, totalAmount, Date.valueOf(LocalDateTime.now().toLocalDate()), "123 Shipping St.", "123 Billing St.");
        return repo.createOrder(newOrder) ? "Order was created" : "Order creation failed";
    }

    @Override
    public String getOrderById(int id) {
        return id <= 0 ? "Invalid order ID." :
                repo.getOrderById(id) != null ? repo.getOrderById(id).toString() : "Order not found";
    }

    @Override
    public String getAllOrders() {
        return repo.getAllOrders().stream()
                .map(Order::toString)
                .collect(Collectors.collectingAndThen(Collectors.joining("\n"),
                        result -> result.isEmpty() ? "No orders available." : result));
    }

    @Override
    public String getUserOrders(int userId) {
        return repo.getOrdersByUserId(userId).stream()
                .map(Order::toString)
                .collect(Collectors.collectingAndThen(Collectors.joining("\n"),
                        result -> result.isEmpty() ? "No orders found for this user." : result));
    }

    @Override
    public String getOrderDetailsById(int orderId) {
        return repo.getOrderDetailsById(orderId);
    }
}
