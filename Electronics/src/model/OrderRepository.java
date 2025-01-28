package model;

import java.sql.*;
import java.util.List;

public class OrderRepository {

    public int createOrder(Order order) {
        String orderQuery = "INSERT INTO orders (user_id, total_price, status) VALUES (?, ?, ?) RETURNING id";

        double totalPrice = calculateTotalPrice(order.getItems());

        try (Connection connection = util.DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(orderQuery)) {

            pstmt.setInt(1, order.getUserId());
            pstmt.setDouble(2, totalPrice);
            pstmt.setString(3, order.getStatus());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private double calculateTotalPrice(List<OrderItem> items) {
        double totalPrice = 0.0;
        for (OrderItem item : items) {
            totalPrice += item.getQuantity() * item.getPrice();
        }
        return totalPrice;
    }

    public void addOrderItems(int orderId, List<OrderItem> items) {
        String itemQuery = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection connection = util.DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(itemQuery)) {

            for (OrderItem item : items) {
                pstmt.setInt(1, orderId);
                pstmt.setInt(2, item.getProductId());
                pstmt.setInt(3, item.getQuantity());
                pstmt.setDouble(4, item.getPrice());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
