package ui;

import models.Expense;
import services.ExpenseService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Dashboard extends JFrame {
    private int userId;
    private ExpenseService expenseService;
    private JTable expenseTable;
    private DefaultTableModel tableModel;

    public Dashboard(int userId) {
        super("Dashboard");
        this.userId = userId;
        this.expenseService = new ExpenseService();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Table Setup
        String[] columns = {"ID", "Amount", "Category", "Date", "Note"};
        tableModel = new DefaultTableModel(columns, 0);
        expenseTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(expenseTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton addExpenseBtn = new JButton("Add Expense");
        JButton logoutBtn = new JButton("Logout");

        buttonPanel.add(addExpenseBtn);
        buttonPanel.add(logoutBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load expenses on startup
        loadExpenses();

        // Button Actions
        addExpenseBtn.addActionListener(e -> {
            new AddExpenseScreen(userId, this).setVisible(true);
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginScreen().setVisible(true);
        });
    }

    public void loadExpenses() {
        List<Expense> expenses = expenseService.getExpensesByUser(userId);
        tableModel.setRowCount(0);  // Clear existing

        for (Expense exp : expenses) {
            Object[] row = {
                exp.getId(),
                exp.getAmount(),
                exp.getCategory(),
                exp.getDate(),
                exp.getNote()
            };
            tableModel.addRow(row);
        }
    }
}