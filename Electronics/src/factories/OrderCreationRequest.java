package factories;

import java.util.List;

public class OrderCreationRequest {
    private int orderId;
    private int userId;
    private List<Integer> productIds;

    public OrderCreationRequest(int orderId, int userId, List<Integer> productIds) {
        this.orderId = orderId;
        this.userId = userId;
        this.productIds = productIds;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }
}
