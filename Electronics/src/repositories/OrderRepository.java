package repositories;

import data.interfaceces.IDB;
import models.Order;
import repositories.interfaces.IOrderRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderRepository implements IOrderRepository {
    private final IDB db;

    public OrderRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createOrder(Order order) {
        Connection connection = null;
        try {
            connection = db.getConnection();
            String sql = "INSERT INTO orders(user_id, order_date, total_amount) VALUES (?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, order.getUserId());
            st.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            st.setDouble(3, order.getTotalPrice());

            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Order getOrderById(int id) {
        Connection connection = null;
        try {
            connection = db.getConnection();
            String sql = "SELECT * FROM orders WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Order(rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDate("order_date"),
                        rs.getDouble("total_amount"));
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        Connection connection = null;
        try {
            connection = db.getConnection();
            String sql = "SELECT * FROM orders";
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(sql);
            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order(rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDate("order_date"),
                        rs.getDouble("total_amount"));
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }
}
