package ui;

import models.Expense;
import services.ExpenseService;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class AddExpenseScreen extends JFrame {
    private int userId;
    private Dashboard dashboard;
    private ExpenseService expenseService;

    private JTextField amountField;
    private JComboBox<String> categoryCombo;
    private JTextField noteField;
    private JSpinner dateSpinner;

    public AddExpenseScreen(int userId, Dashboard dashboard) {
        super("Add Expense");
        this.userId = userId;
        this.dashboard = dashboard;
        this.expenseService = new ExpenseService();

        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);

        add(new JLabel("Category:"));
        // Example categories - update as per your CategoryService
        categoryCombo = new JComboBox<>(new String[]{"Food", "Transport", "Entertainment", "Bills", "Others"});
        add(categoryCombo);

        add(new JLabel("Date:"));
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        add(dateSpinner);

        add(new JLabel("Note:"));
        noteField = new JTextField();
        add(noteField);

        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");
        add(addButton);
        add(cancelButton);

        addButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText().trim());
                String category = (String) categoryCombo.getSelectedItem();
                Date date = (Date) dateSpinner.getValue();
                String note = noteField.getText().trim();

                Expense expense = new Expense(userId, amount, category, date, note);
                boolean success = expenseService.addExpense(expense);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Expense added successfully!");
                    dashboard.loadExpenses();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add expense.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }
}