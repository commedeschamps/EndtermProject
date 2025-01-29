package controllers.interfaces;

public interface IOrderController {
    String createOrder(int userId, double totalAmount, String deliveryMethod, String paymentMethod);
    String getOrderById(int id);
    String getAllOrders();
}
