package repositories;

import data.interfaceces.IDB;
import models.User;
import repositories.interfaces.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserRepository implements IUserRepository {
    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createUser(User user) {
        if (user == null) {
            System.out.println("Invalid input: User cannot be null.");
            return false;
        }
        if (user.getName() == null || user.getName().trim().isEmpty() ||
                user.getSurname() == null || user.getSurname().trim().isEmpty() ||
                user.getEmail() == null || user.getEmail().trim().isEmpty() ||
                user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            System.out.println("Invalid input: Name, Surname, Email, and Password cannot be empty.");
            return false;
        }
        if (user.getBalance() < 0) {
            System.out.println("Invalid input: Balance cannot be negative.");
            return false;
        }

        if (getUserByEmail(user.getEmail()) != null) {
            System.out.println("Error: Email is already in use.");
            return false;
        }

        String sql = "INSERT INTO users(name, surname, gender, email, password, balance) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, user.getName().trim());
            st.setString(2, user.getSurname().trim());
            st.setBoolean(3, user.getGender());
            st.setString(4, user.getEmail().trim());
            st.setString(5, user.getPassword().trim());
            st.setDouble(6, user.getBalance());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error while creating user: " + e.getMessage());
        }
        return false;
    }

    @Override
    public User getUserById(int id) {
        if (id <= 0) {
            System.out.println("Invalid input: User ID must be positive.");
            return null;
        }

        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("SQL error while retrieving user by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection connection = db.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
            return users.isEmpty() ? Collections.emptyList() : users;
        } catch (SQLException e) {
            System.out.println("SQL error while retrieving all users: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public boolean verifyUserCredentials(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("Invalid input: Email and Password cannot be empty.");
            return false;
        }

        return getUserByEmailAndPassword(email, password) != null;
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Invalid input: Email cannot be empty.");
            return null;
        }

        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, email.trim());
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("SQL error while retrieving user by email: " + e.getMessage());
        }
        return null;
    }

    public User getUserByEmailAndPassword(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("Invalid input: Email and Password cannot be empty.");
            return null;
        }

        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, email.trim());
            st.setString(2, password.trim());
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("SQL error while verifying user credentials: " + e.getMessage());
        }
        return null;
    }

    public boolean updateUserBalance(int userId, double newBalance) {
        if (userId <= 0) {
            System.out.println("Invalid input: User ID must be positive.");
            return false;
        }
        if (newBalance < 0) {
            System.out.println("Invalid input: Balance cannot be negative.");
            return false;
        }

        String sql = "UPDATE users SET balance = ? WHERE id = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            st.setDouble(1, newBalance);
            st.setInt(2, userId);

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error while updating user balance: " + e.getMessage());
        }
        return false;
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getBoolean("gender"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getDouble("balance")
        );
    }
}
