package User;

import model.User;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    public void registerUser(User user) {
        String query = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getRole());
            pstmt.executeUpdate();

            System.out.println("User registered successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> loginUser(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<String> getProductCategories() {
        List<String> categories = new ArrayList<>();
        String query = "SELECT DISTINCT category FROM products";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<model.Product> getProductsByCategory(String category) {
        List<model.Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE category = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                model.Product product = new model.Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}