package repositories;

import data.interfaceces.IDB;
import models.Cart;
import models.OrderItem;
import repositories.interfaces.ICartRepository;

import java.sql.*;

public class CartRepository implements ICartRepository {
    private final IDB db;

    public CartRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean addItemToCart(int userId, int productId, int quantity, double price) {
        if (userId <= 0 || productId <= 0 || quantity <= 0 || price < 0) {
            System.out.println("Invalid input: userId, productId, and quantity must be positive; price cannot be negative.");
            return false;
        }

        try (Connection connection = db.getConnection()) {
            String checkSql = "SELECT quantity FROM cart WHERE user_id = ? AND product_id = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                checkStmt.setInt(1, userId);
                checkStmt.setInt(2, productId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    String updateSql = "UPDATE cart SET quantity = quantity + ?, price = ? WHERE user_id = ? AND product_id = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, quantity);
                        updateStmt.setDouble(2, price);
                        updateStmt.setInt(3, userId);
                        updateStmt.setInt(4, productId);
                        return updateStmt.executeUpdate() > 0;
                    }
                } else {
                    String insertSql = "INSERT INTO cart(user_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, userId);
                        insertStmt.setInt(2, productId);
                        insertStmt.setInt(3, quantity);
                        insertStmt.setDouble(4, price);
                        return insertStmt.executeUpdate() > 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error while adding item to cart: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Cart getCartByUserId(int userId) {
        if (userId <= 0) {
            System.out.println("Invalid input: userId must be positive.");
            return null;
        }

        try (Connection connection = db.getConnection()) {
            String sql = "SELECT * FROM cart WHERE user_id = ?";
            try (PreparedStatement st = connection.prepareStatement(sql)) {
                st.setInt(1, userId);
                ResultSet rs = st.executeQuery();
                Cart cart = new Cart(userId);
                while (rs.next()) {
                    OrderItem item = new OrderItem(
                            rs.getInt("id"),
                            rs.getInt("product_id"),
                            rs.getInt("quantity"),
                            rs.getDouble("price")
                    );
                    cart.addItem(item);
                }
                return cart.getItems().isEmpty() ? null : cart;
            }
        } catch (SQLException e) {
            System.out.println("SQL error while retrieving cart: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean clearCart(int userId) {
        if (userId <= 0) {
            System.out.println("Invalid input: userId must be positive.");
            return false;
        }

        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement("DELETE FROM cart WHERE user_id = ?")) {
            st.setInt(1, userId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error while clearing cart: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateCartItemQuantity(int userId, int productId, int quantity) {
        if (userId <= 0 || productId <= 0 || quantity <= 0) {
            System.out.println("Invalid input: userId, productId, and quantity must be positive.");
            return false;
        }

        try (Connection connection = db.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(
                     "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?")) {
            updateStmt.setInt(1, quantity);
            updateStmt.setInt(2, userId);
            updateStmt.setInt(3, productId);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error while updating cart item: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeItemFromCart(int userId, int productId) {
        if (userId <= 0 || productId <= 0) {
            System.out.println("Invalid input: userId and productId must be positive.");
            return false;
        }

        try (Connection connection = db.getConnection();
             PreparedStatement deleteStmt = connection.prepareStatement(
                     "DELETE FROM cart WHERE user_id = ? AND product_id = ?")) {
            deleteStmt.setInt(1, userId);
            deleteStmt.setInt(2, productId);
            return deleteStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error while removing cart item: " + e.getMessage());
        }
        return false;
    }
}
