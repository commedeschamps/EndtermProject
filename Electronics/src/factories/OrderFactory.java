package factories;

import models.Order;

public class OrderFactory implements Factory<Order> {

    @Override
    public Order create(Object request) {
        if (request instanceof OrderCreationRequest) {
            OrderCreationRequest req = (OrderCreationRequest) request;
            return new Order(req.getOrderId(), req.getUserId(), req.getProductIds());
        } else {
            throw new IllegalArgumentException("Invalid request type");
        }
    }
}
