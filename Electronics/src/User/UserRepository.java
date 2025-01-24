package User;

import model.User;
import util.DatabaseConnection;

import java.sql.*;
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
}
