package ui;

import models.User;
import services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupScreen extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private UserService userService;

    public SignupScreen() {
        super("Signup");
        userService = new UserService();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton signupButton = new JButton("Signup");
        JButton backButton = new JButton("Back to Login");

        panel.add(signupButton);
        panel.add(backButton);

        add(panel, BorderLayout.CENTER);

        // Actions
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(SignupScreen.this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                User user = new User(name, email, password);
                boolean success = userService.register(user);

                if (success) {
                    JOptionPane.showMessageDialog(SignupScreen.this, "Registration successful! Please login.");
                    dispose();
                    new LoginScreen().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(SignupScreen.this, "Registration failed. Email may already be in use.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new LoginScreen().setVisible(true);
        });
    }
}