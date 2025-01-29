package controllers;

import controllers.interfaces.IOrderController;
import models.Order;
import repositories.interfaces.IOrderRepository;

import java.util.Date;
import java.util.List;

public class OrderController implements IOrderController {
    private final IOrderRepository repo;

    public OrderController(IOrderRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createOrder(int userId, double totalAmount) {
        Order order = new Order(userId, new Date(), totalAmount);
        boolean created = repo.createOrder(order);
        return (created) ? "Order was created" : "Order creation failed";
    }

    @Override
    public String getOrderById(int id) {
        Order order = repo.getOrderById(id);
        return (order == null) ? "Order not found" : order.toString();
    }

    @Override
    public String getAllOrders() {
        List<Order> orders = repo.getAllOrders();
        StringBuilder response = new StringBuilder();
        for (Order order : orders) {
            response.append(order.toString()).append("\n");
        }
        return response.toString();
    }
}
