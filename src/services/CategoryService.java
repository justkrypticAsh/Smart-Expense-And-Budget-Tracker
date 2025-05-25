package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import models.Category;

public class CategoryService {

    public void addCategory(Category category) {
        String sql = "INSERT INTO categories (name) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.executeUpdate();
            System.out.println("Category added: " + category.getName());

        } catch (SQLException e) {
            System.err.println("Error adding category.");
            e.printStackTrace();
        }
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Category category = new Category(rs.getInt("id"), rs.getString("name"));
                categories.add(category);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving categories.");
            e.printStackTrace();
        }

        return categories;
    }
}