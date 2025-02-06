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
        return repo.createOrderItem(new OrderItem(orderId, productId, quantity, price))
                ? "OrderItem was created" : "OrderItem creation failed";
    }

    @Override
    public String getOrderItemById(int id) {
        return id <= 0 ? "Invalid OrderItem ID." :
                repo.getOrderItemById(id) != null ? repo.getOrderItemById(id).toString() : "OrderItem not found";
    }

    @Override
    public String getAllOrderItems() {
        return repo.getAllOrderItems().stream()
                .map(OrderItem::toString)
                .collect(Collectors.collectingAndThen(Collectors.joining("\n"),
                        result -> result.isEmpty() ? "No OrderItems available." : result));
    }

    @Override
    public double calculateOrderItemTotalPrice(int quantity, double price) {
        return quantity * price;
    }
}
