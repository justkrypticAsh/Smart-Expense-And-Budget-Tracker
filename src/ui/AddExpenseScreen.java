package ui;

import java.awt.*;
import java.util.Date;
import javax.swing.*;
import models.Expense;
import services.ExpenseService;
import services.UserService;

public class AddExpenseScreen extends JFrame {
    private int userId;
    private ExpenseService expenseService;
    private UserService userService;

    private JTextField amountField;
    private JTextField categoryField;
    private JTextField noteField;
    private JSpinner dateSpinner;

    public AddExpenseScreen(int userId, ExpenseService expenseService, UserService userService) {
        this.userId = userId;
        this.expenseService = expenseService;
        this.userService = userService;

        setTitle("Add Expense");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);

        add(new JLabel("Category:"));
        categoryField = new JTextField();
        add(categoryField);

        add(new JLabel("Date:"));
        dateSpinner = new JSpinner(new SpinnerDateModel());
        add(dateSpinner);

        add(new JLabel("Note:"));
        noteField = new JTextField();
        add(noteField);

        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(e -> {
            try {
                String amountText = amountField.getText().trim();
                String category = categoryField.getText().trim();
                String note = noteField.getText().trim();
                Date date = (Date) dateSpinner.getValue();

                if (amountText.isEmpty() || category.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Amount and Category are required fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Amount must be greater than zero.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Expense expense = new Expense(userId, amount, category, date, note);

                boolean success;
                try {
                    success = expenseService.addExpense(expense);
                } catch (Exception dbEx) {
                    JOptionPane.showMessageDialog(this, "Database error: " + dbEx.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (success) {
                    JOptionPane.showMessageDialog(this, "Expense added successfully.");
                    dispose();
                    new Dashboard(userId, expenseService, userService).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add expense.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount format. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(addButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            dispose();
            new Dashboard(userId, expenseService, userService).setVisible(true);
        });
        add(cancelButton);
    }
}
