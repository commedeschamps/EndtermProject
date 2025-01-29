package repositories;

import data.interfaceces.IDB;
import models.Product;
import repositories.interfaces.IProductRepository;

import java.sql.*;
import java.util.List;

public class ProductRepository implements IProductRepository {
    private final IDB db;

    public ProductRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createProduct(Product product) {
        String sql = "INSERT INTO products (name, description, price, quantity, category) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantity());
            stmt.setString(5, product.getCategory());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error creating product: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Product getProductById(int id) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public void updateProduct(Product product) {
    }

    @Override
    public void deleteProduct(int id) {
    }
}