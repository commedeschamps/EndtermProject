package controllers;

import controllers.interfaces.IOrderController;
import models.Order;
import repositories.interfaces.IOrderRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderController implements IOrderController {
    private final IOrderRepository repo;

    public OrderController(IOrderRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createOrder(int userId, double totalAmount, String deliveryMethod, String paymentMethod) {
        Order order = new Order(userId, new Date(), totalAmount, deliveryMethod, paymentMethod);
        boolean created = repo.createOrder(order);
        return (created) ? "Order was created successfully" : "Order creation failed";
    }

    @Override
    public String getOrderById(int id) {
        Order order = repo.getOrderById(id);
        return (order == null) ? "Order not found" : order.toString();
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
}
