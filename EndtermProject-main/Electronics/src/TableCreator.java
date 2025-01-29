import java.sql.Connection;
import java.sql.Statement;

public class TableCreator {

    public static void main(String[] args) {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS products (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    description TEXT,
                    category VARCHAR(50),
                    price DECIMAL(10, 2) NOT NULL,
                    quantity INT DEFAULT 0
                );
                """;

        try (Connection connection = util.DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(createTableSQL);
            System.out.println("Table 'products' created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}