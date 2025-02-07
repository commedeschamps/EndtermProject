package repositories;

import data.interfaceces.IDB;
import models.OrderItem;
import repositories.interfaces.IOrderItemRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderItemRepository implements IOrderItemRepository {
    private final IDB db;

    public OrderItemRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createOrderItem(OrderItem orderItem) {
        if (orderItem == null) {
            System.out.println("Invalid input: OrderItem cannot be null.");
            return false;
        }
        if (orderItem.getOrderId() <= 0 || orderItem.getProductId() <= 0 || orderItem.getQuantity() <= 0 || orderItem.getPrice() < 0) {
            System.out.println("Invalid input: Order ID, Product ID, and Quantity must be positive; Price cannot be negative.");
            return false;
        }

        String sql = "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, orderItem.getOrderId());
            st.setInt(2, orderItem.getProductId());
            st.setInt(3, orderItem.getQuantity());
            st.setDouble(4, orderItem.getPrice());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error while creating order item: " + e.getMessage());
        }
        return false;
    }

    public List<OrderItem> getItemsByOrderId(int orderId) {
        if (orderId <= 0) {
            System.out.println("Invalid input: Order ID must be positive.");
            return Collections.emptyList();
        }

        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, orderId);
            ResultSet rs = st.executeQuery();
            List<OrderItem> items = new ArrayList<>();

            while (rs.next()) {
                items.add(new OrderItem(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }
            return items;
        } catch (SQLException e) {
            System.out.println("SQL error while retrieving order items: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public OrderItem getOrderItemById(int id) {
        if (id <= 0) {
            System.out.println("Invalid input: OrderItem ID must be positive.");
            return null;
        }

        String sql = "SELECT * FROM order_items WHERE id = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new OrderItem(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL error while retrieving order item: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        String sql = "SELECT * FROM order_items";
        try (Connection connection = db.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            List<OrderItem> orderItems = new ArrayList<>();
            while (rs.next()) {
                orderItems.add(new OrderItem(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }
            return orderItems.isEmpty() ? Collections.emptyList() : orderItems;
        } catch (SQLException e) {
            System.out.println("SQL error while retrieving all order items: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public boolean removeOrderItem(int orderId, int productId) {
        if (orderId <= 0 || productId <= 0) {
            System.out.println("Invalid input: Order ID and Product ID must be positive.");
            return false;
        }

        String sql = "DELETE FROM order_items WHERE order_id = ? AND product_id = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, orderId);
            st.setInt(2, productId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error while removing order item: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteOrderItem(int id) {
        if (id <= 0) {
            System.out.println("Invalid input: OrderItem ID must be positive.");
            return false;
        }

        String sql = "DELETE FROM order_items WHERE id = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error while deleting order item: " + e.getMessage());
        }
        return false;
    }
}
