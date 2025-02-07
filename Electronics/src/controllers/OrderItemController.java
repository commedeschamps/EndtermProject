package controllers;

import controllers.interfaces.IOrderItemController;
import models.OrderItem;
import repositories.interfaces.IOrderItemRepository;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemController implements IOrderItemController {
    private final IOrderItemRepository repo;

    public OrderItemController(IOrderItemRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createOrderItem(int orderId, int productId, int quantity, double price) {
        if (orderId <= 0) {
            return "Invalid Order ID. It must be a positive number.";
        }
        if (productId <= 0) {
            return "Invalid Product ID. It must be a positive number.";
        }
        if (quantity <= 0) {
            return "Invalid quantity. It must be a positive number.";
        }
        if (price < 0) {
            return "Invalid price. It cannot be negative.";
        }

        OrderItem orderItem = new OrderItem(orderId, productId, quantity, price);
        return repo.createOrderItem(orderItem) ? "OrderItem was created" : "OrderItem creation failed";
    }

    @Override
    public String getOrderItemById(int id) {
        if (id <= 0) {
            return "Invalid OrderItem ID. It must be a positive number.";
        }

        OrderItem orderItem = repo.getOrderItemById(id);
        return (orderItem != null) ? orderItem.toString() : "OrderItem not found";
    }

    @Override
    public String getAllOrderItems() {
        List<OrderItem> orderItems = repo.getAllOrderItems();
        if (orderItems == null || orderItems.isEmpty()) {
            return "No OrderItems available.";
        }

        return orderItems.stream()
                .map(OrderItem::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public double calculateOrderItemTotalPrice(int quantity, double price) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive number.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        return quantity * price;
    }
}
