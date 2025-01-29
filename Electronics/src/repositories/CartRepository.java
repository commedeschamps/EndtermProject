package repositories;

import data.interfaceces.IDB;
import models.Cart;
import models.OrderItem;
import repositories.interfaces.ICartRepository;

import java.sql.*;
import java.util.ArrayList;

public class CartRepository implements ICartRepository {
    private final IDB db;

    public CartRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean addItemToCart(int userId, OrderItem item) {
        Connection connection = null;
        try {
            connection = db.getConnection();
            String sql = "INSERT INTO cart(user_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, userId);
            st.setInt(2, item.getProductId());
            st.setInt(3, item.getQuantity());
            st.setDouble(4, item.getPrice());

            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Cart getCartByUserId(int userId) {
        Connection connection = null;
        try {
            connection = db.getConnection();
            String sql = "SELECT * FROM cart WHERE user_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);

            ResultSet rs = st.executeQuery();
            Cart cart = new Cart(userId);
            while (rs.next()) {
                OrderItem item = new OrderItem(rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"));
                cart.addItem(item);
            }
            return cart;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean clearCart(int userId) {
        Connection connection = null;
        try {
            connection = db.getConnection();
            String sql = "DELETE FROM cart WHERE user_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);

            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }
}