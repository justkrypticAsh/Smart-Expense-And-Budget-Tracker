package ui;

import models.Expense;
import services.ExpenseService;
import services.UserService;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

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
                double amount = Double.parseDouble(amountField.getText());
                String category = categoryField.getText();
                Date date = (Date) dateSpinner.getValue();
                String note = noteField.getText();

                Expense expense = new Expense(userId, amount, category, date, note);
                boolean success = expenseService.addExpense(expense);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Expense added successfully.");
                    dispose();
                    new Dashboard(userId, expenseService, userService).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add expense.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
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
