package controllers.interfaces;

public interface IOrderController {
    String createOrder(int userId, double totalAmount);
    String getOrderById(int id);
    String getAllOrders();
}