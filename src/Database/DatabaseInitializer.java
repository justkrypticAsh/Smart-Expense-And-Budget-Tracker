package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "expense_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Lko567u";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            String createDb = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            stmt.executeUpdate(createDb);
            System.out.println("Database created or already exists.");

            try (Connection dbConn = DriverManager.getConnection(DB_URL + DB_NAME, USER, PASSWORD);
                 Statement dbStmt = dbConn.createStatement()) {

                String createUsersTable = """
                    CREATE TABLE IF NOT EXISTS users (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL
                    );
                """;

                String createExpensesTable = """
                    CREATE TABLE IF NOT EXISTS expenses (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT NOT NULL,
                        amount DOUBLE NOT NULL,
                        category VARCHAR(255),
                        date DATE NOT NULL,
                        note TEXT,
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                    );
                """;

                String createCategoriesTable = """
                    CREATE TABLE IF NOT EXISTS categories (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL
                    );
                """;

                dbStmt.execute(createUsersTable);
                dbStmt.execute(createExpensesTable);
                dbStmt.execute(createCategoriesTable);

                System.out.println("All tables created successfully.");
            }

        } catch (SQLException e) {
            System.err.println("Error during database setup.");
            e.printStackTrace();
        }
    }
}