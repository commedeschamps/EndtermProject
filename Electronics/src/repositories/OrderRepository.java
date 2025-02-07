package repositories;

import data.interfaceces.IDB;
import models.Order;
import models.OrderItem;
import repositories.interfaces.IOrderRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderRepository implements IOrderRepository {
    private final IDB db;

    public OrderRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createOrder(Order order) {
        if (order == null) {
            System.out.println("Invalid input: Order cannot be null.");
            return false;
        }
        if (order.getUserId() <= 0 || order.getTotalAmount() <= 0) {
            System.out.println("Invalid input: User ID and Total Amount must be positive.");
            return false;
        }

        String sql = "INSERT INTO orders(user_id, order_date, total_amount, status, delivery_method, payment_method) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            st.setInt(1, order.getUserId());
            st.setTimestamp(2, new Timestamp(order.getOrderDate().getTime()));
            st.setDouble(3, order.getTotalAmount());
            st.setString(4, order.getStatus());
            st.setString(5, order.getDeliveryMethod());
            st.setString(6, order.getPaymentMethod());

            int affectedRows = st.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        int orderId = rs.getInt(1);
                        for (OrderItem item : order.getOrderItems()) {
                            addOrderItem(orderId, item);
                        }
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error while creating order: " + e.getMessage());
        }
        return false;
    }

    private void addOrderItem(int orderId, OrderItem item) {
        if (orderId <= 0 || item.getProductId() <= 0 || item.getQuantity() <= 0 || item.getPrice() < 0) {
            System.out.println("Invalid order item details.");
            return;
        }

        String sql = "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, orderId);
            st.setInt(2, item.getProductId());
            st.setInt(3, item.getQuantity());
            st.setDouble(4, item.getPrice());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL error while adding order item: " + e.getMessage());
        }
    }

    @Override
    public Order getOrderById(int id) {
        if (id <= 0) {
            System.out.println("Invalid input: Order ID must be positive.");
            return null;
        }

        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        new Date(rs.getTimestamp("order_date").getTime()),
                        rs.getDouble("total_amount")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL error while retrieving order by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = db.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        new Date(rs.getTimestamp("order_date").getTime()),
                        rs.getDouble("total_amount")
                ));
            }
            return orders.isEmpty() ? Collections.emptyList() : orders;
        } catch (SQLException e) {
            System.out.println("SQL error while retrieving all orders: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        if (userId <= 0) {
            System.out.println("Invalid input: User ID must be positive.");
            return Collections.emptyList();
        }

        String sql = "SELECT * FROM orders WHERE user_id = ?";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        new Date(rs.getTimestamp("order_date").getTime()),
                        rs.getDouble("total_amount")
                ));
            }
            return orders.isEmpty() ? Collections.emptyList() : orders;
        } catch (SQLException e) {
            System.out.println("SQL error while retrieving orders by user ID: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public String getOrderDetailsById(int orderId) {
        if (orderId <= 0) {
            return "Invalid input: Order ID must be positive.";
        }

        String sql = """
        SELECT
            o.id AS order_id,
            o.order_date,
            o.status,
            o.delivery_method,
            o.payment_method,
            o.total_amount,
            u.name AS user_name,
            u.email AS user_email,
            oi.product_id,
            p.name AS product_name,
            p.price AS product_price,
            oi.quantity,
            oi.price AS item_price
        FROM orders o
        JOIN users u ON o.user_id = u.id
        JOIN order_items oi ON o.id = oi.order_id
        JOIN products p ON oi.product_id = p.id
        WHERE o.id = ?;
        """;

        StringBuilder result = new StringBuilder();
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            boolean orderFound = false;
            while (rs.next()) {
                if (!orderFound) {
                    result.append("\nOrder Details:\n")
                            .append("Order ID: ").append(rs.getInt("order_id")).append("\n")
                            .append("Order Date: ").append(rs.getTimestamp("order_date")).append("\n")
                            .append("Status: ").append(rs.getString("status")).append("\n")
                            .append("Delivery Method: ").append(rs.getString("delivery_method")).append("\n")
                            .append("Payment Method: ").append(rs.getString("payment_method")).append("\n")
                            .append("Total Amount: ").append(rs.getDouble("total_amount")).append("\n")
                            .append("Customer: ").append(rs.getString("user_name")).append(" (").append(rs.getString("user_email")).append(")\n")
                            .append("Products:\n");
                    orderFound = true;
                }
                result.append("- Product ID: ").append(rs.getInt("product_id"))
                        .append(", Name: ").append(rs.getString("product_name"))
                        .append(", Price: ").append(rs.getDouble("product_price"))
                        .append(", Quantity: ").append(rs.getInt("quantity"))
                        .append(", Total Item Price: ").append(rs.getDouble("item_price"))
                        .append("\n");
            }

            return orderFound ? result.toString() : "Order with ID " + orderId + " not found.";
        } catch (SQLException e) {
            return "Error retrieving order details: " + e.getMessage();
        }
    }
}
