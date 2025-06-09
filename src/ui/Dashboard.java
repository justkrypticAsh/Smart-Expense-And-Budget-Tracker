package ui;

import models.Expense;
import models.User;
import services.ExpenseService;
import services.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Dashboard extends JFrame {
    private int userId;
    private ExpenseService expenseService;
    private UserService userService;

    private JTable expenseTable;
    private DefaultTableModel tableModel;
    private JLabel budgetLabel;
    private JLabel totalExpenseLabel;
    private JPanel chartContainer;
    private ChartPanel chartPanel;

    public Dashboard(int userId, ExpenseService expenseService, UserService userService) {
        this.userId = userId;
        this.expenseService = expenseService;
        this.userService = userService;

        setTitle("Dashboard");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"ID", "Amount", "Category", "Date", "Note"}, 0);
        expenseTable = new JTable(tableModel);

        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        budgetLabel = new JLabel();
        totalExpenseLabel = new JLabel();
        infoPanel.add(budgetLabel);
        infoPanel.add(totalExpenseLabel);

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(expenseTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton addExpenseButton = new JButton("Add Expense");
        addExpenseButton.addActionListener(e -> {
            dispose();
            new AddExpenseScreen(userId, expenseService, userService).setVisible(true);
        });
        buttonPanel.add(addExpenseButton);

        JButton setBudgetButton = new JButton("Set Monthly Budget");
        setBudgetButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter Monthly Budget:");
            if (input != null) {
                try {
                    double budget = Double.parseDouble(input);
                    User user = getCurrentUser();
                    if (user != null) {
                        user.setMonthlyBudget(budget);
                        JOptionPane.showMessageDialog(this, "Monthly budget updated.");
                        updateBudgetInfo();
                        updateChart();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount.");
                }
            }
        });
        buttonPanel.add(setBudgetButton);

        JButton reportButton = new JButton("Category Report");
        reportButton.addActionListener(e -> showCategoryReport());
        buttonPanel.add(reportButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginScreen(userService).setVisible(true);
        });
        buttonPanel.add(logoutButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        chartContainer = new JPanel(new BorderLayout());
        mainPanel.add(chartContainer, BorderLayout.EAST);

        add(mainPanel);

        createChartPanel();
        loadExpenses();
    }

    private void loadExpenses() {
        List<Expense> expenses = expenseService.getExpensesByUser(userId);
        tableModel.setRowCount(0);
        for (Expense e : expenses) {
            tableModel.addRow(new Object[]{
                e.getId(), e.getAmount(), e.getCategory(), e.getDate(), e.getNote()
            });
        }
        updateBudgetInfo();
        updateChart();
    }

    private void updateBudgetInfo() {
        User user = getCurrentUser();
        if (user != null) {
            double budget = user.getMonthlyBudget();

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;

            double totalExpense = expenseService.getTotalExpenseForMonth(userId, year, month);

            if (budget > 0 && totalExpense > budget) {
                budgetLabel.setForeground(Color.RED);
                budgetLabel.setText("Monthly Budget: ₹" + budget + " (Exceeded!)");
            } else {
                budgetLabel.setForeground(Color.BLACK);
                budgetLabel.setText("Monthly Budget: ₹" + (budget > 0 ? budget : "Not Set"));
            }

            totalExpenseLabel.setText("Total Expenses This Month: ₹" + totalExpense);
        }
    }

    private void createChartPanel() {
        DefaultPieDataset dataset = createDataset();
        JFreeChart chart = ChartFactory.createPieChart("Budget vs Expenses", dataset, true, true, false);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(300, 300));
        chartContainer.add(chartPanel, BorderLayout.CENTER);
    }

    private DefaultPieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        User user = getCurrentUser();
        if (user != null) {
            double budget = user.getMonthlyBudget();
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            double expenses = expenseService.getTotalExpenseForMonth(userId, year, month);
            double remaining = budget - expenses;
            if (remaining < 0) remaining = 0;
            dataset.setValue("Expenses", expenses);
            dataset.setValue("Remaining Budget", remaining);
        }
        return dataset;
    }

    private void updateChart() {
        DefaultPieDataset dataset = createDataset();
        PiePlot plot = (PiePlot) chartPanel.getChart().getPlot();
        plot.setDataset(dataset);
    }

    private void showCategoryReport() {
        Map<String, Double> categoryTotals = expenseService.getTotalByCategory(userId);
        StringBuilder report = new StringBuilder("<html><b>Category Wise:</b><br>");
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            report.append(entry.getKey()).append(": ₹").append(entry.getValue()).append("<br>");
        }
        report.append("</html>");
        JLabel reportLabel = new JLabel(report.toString());
        reportLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, reportLabel, "Category-wise Report", JOptionPane.INFORMATION_MESSAGE);
    }

    private User getCurrentUser() {
        for (User u : userService.getUsers()) {
            if (u.getId() == userId) {
                return u;
            }
        }
        return null;
    }
}
