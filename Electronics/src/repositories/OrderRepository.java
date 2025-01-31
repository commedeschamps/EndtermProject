package repositories;

import data.interfaceces.IDB;
import models.Order;
import repositories.interfaces.IOrderRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IOrderRepository {
    private final IDB db;

    public OrderRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createOrder(Order order) {
        String sql = "INSERT INTO orders(user_id, order_date, total_amount) VALUES (?, ?, ?)";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            st.setInt(1, order.getUserId());
            st.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            st.setDouble(3, order.getTotalAmount());

            int affectedRows = st.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        order.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error while creating order: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Order getOrderById(int id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    java.sql.Date sqlDate = rs.getDate("order_date");
                    java.util.Date orderDate = new java.util.Date(sqlDate.getTime());

                    return new Order(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            orderDate, 
                            rs.getDouble("total_amount")
                    );
                }
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
                java.sql.Date sqlDate = rs.getDate("order_date");
                java.util.Date orderDate = new java.util.Date(sqlDate.getTime());

                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        orderDate, 
                        rs.getDouble("total_amount")
                ));
            }
            return orders;
        } catch (SQLException e) {
            System.out.println("SQL error while retrieving all orders: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, userId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    java.sql.Date sqlDate = rs.getDate("order_date");
                    java.util.Date orderDate = new java.util.Date(sqlDate.getTime());

                    orders.add(new Order(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            orderDate
                            rs.getDouble("total_amount")
                    ));
                }
            }
            return orders;
        } catch (SQLException e) {
            System.out.println("SQL error while retrieving orders by user ID: " + e.getMessage());
        }
        return null;
    }
}
