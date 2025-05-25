package ui;

import javax.swing.*;
import java.awt.event.*;
import services.UserService;
import services.ExpenseService;

public class LoginScreen extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;

    private UserService userService;
    private ExpenseService expenseService;

    public LoginScreen(UserService userService) {
        this.userService = userService;
        this.expenseService = ExpenseService.getInstance();

        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 20, 80, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(100, 20, 160, 25);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 60, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 60, 160, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 100, 80, 25);
        add(loginButton);

        signupButton = new JButton("Signup");
        signupButton.setBounds(190, 100, 80, 25);
        add(signupButton);

        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            int userId = userService.login(email, password);
            if (userId != -1) {
                JOptionPane.showMessageDialog(null, "Login successful!");
                dispose();
                SwingUtilities.invokeLater(() -> new Dashboard(userId, expenseService, userService).setVisible(true));
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials.");
            }
        });

        signupButton.addActionListener(e -> {
            dispose();
            new SignupScreen(userService).setVisible(true);
        });
    }
}
