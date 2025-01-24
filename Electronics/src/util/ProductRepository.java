package util;

import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private Connection connection;

    public ProductRepository() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Product> searchProductsByName(String name) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE LOWER(name) LIKE LOWER(?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
                products.add(product);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity") // Убедитесь, что столбец quantity существует в вашей таблице
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
    public void addProduct(Product product) {
        String query="INSERT INTO products (name, description, category, price, quantity) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement= connection.prepareStatement(query)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setString(3, product.getCategory());
            preparedStatement.setDouble(4,product.getPrice());
            preparedStatement.setInt(5,product.getQuantity());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void updateProduct(Product product) {
        String query="UPDATE products SET name= ?, description=?, category=?, price=?, quantity=? WHERE id=?";

        try(PreparedStatement preparedStatement= connection.prepareStatement(query)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setString(3, product.getCategory());
            preparedStatement.setDouble(4,product.getPrice());
            preparedStatement.setInt(5,product.getQuantity());
            preparedStatement.setInt(6,product.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteProduct(int product) {
        String query="DELETE FROM products WHERE id=?";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, product);
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

}
