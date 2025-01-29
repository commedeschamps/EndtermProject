package controllers;

import controllers.interfaces.IOrderController;
import models.Order;
import repositories.interfaces.IOrderRepository;
import java.util.stream.Collectors;


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
    public String getAllProducts() {
        List<Product> products = repo.getAllProducts();

        if (products == null || products.isEmpty()) {
            return "No products available.";
        }

        return products.stream()
                .map(Product::toString)
                .collect(Collectors.joining("\n")); 
}

}
