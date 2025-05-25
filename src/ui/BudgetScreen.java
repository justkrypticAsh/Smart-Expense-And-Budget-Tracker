package ui;

import services.UserService;
import models.User;

import javax.swing.*;
import java.awt.event.*;

public class BudgetScreen extends JFrame {
    private UserService userService;
    private User user;

    private JTextField budgetField;
    private JButton saveButton;

    public BudgetScreen(UserService userService, User user) {
        this.userService = userService;
        this.user = user;

        setTitle("Set Monthly Budget");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel label = new JLabel("Monthly Budget:");
        label.setBounds(10, 20, 120, 25);
        add(label);

        budgetField = new JTextField(String.valueOf(user.getMonthlyBudget()));
        budgetField.setBounds(140, 20, 120, 25);
        add(budgetField);

        saveButton = new JButton("Save");
        saveButton.setBounds(100, 70, 80, 30);
        add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double budget = Double.parseDouble(budgetField.getText());
                    user.setMonthlyBudget(budget);
                    JOptionPane.showMessageDialog(null, "Budget updated!");
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter valid number.");
                }
            }
        });
    }
}
