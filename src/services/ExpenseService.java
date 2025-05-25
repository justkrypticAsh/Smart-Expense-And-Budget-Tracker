package services;

import database.DBConnection;
import models.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseService {

    public void addExpense(Expense expense) {
        String sql = "INSERT INTO expenses (user_id, amount, category, date, note) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, expense.getUserId());
            stmt.setDouble(2, expense.getAmount());
            stmt.setString(3, expense.getCategory());
            stmt.setDate(4, new java.sql.Date(expense.getDate().getTime()));
            stmt.setString(5, expense.getNote());

            stmt.executeUpdate();
            System.out.println("Expense added successfully.");

        } catch (SQLException e) {
            System.err.println("Error adding expense.");
            e.printStackTrace();
        }
    }

    public List<Expense> getExpensesByUserId(int userId) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                String category = rs.getString("category");
                Date date = rs.getDate("date");
                String note = rs.getString("note");

                Expense expense = new Expense(id, userId, amount, category, date, note);
                expenses.add(expense);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving expenses.");
            e.printStackTrace();
        }

        return expenses;
    }
}