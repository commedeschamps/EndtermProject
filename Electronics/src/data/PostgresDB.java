package data;

import data.interfaceces.IDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    private String connectionUrl;
    private String username;
    private String password;
    private Connection connection;

    public PostgresDB(String connectionUrl, String username, String password) {
        this.setConnectionUrl(connectionUrl);
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getConnectionUrl() {
        return this.connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                return this.connection;
            } else {
                Class.forName("org.postgresql.Driver");
                this.connection = DriverManager.getConnection(connectionUrl, this.username, this.password);
                return this.connection;
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            return null;
        }
    }

    public void close() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection" + e.getMessage());
            }
        }
    }
}
