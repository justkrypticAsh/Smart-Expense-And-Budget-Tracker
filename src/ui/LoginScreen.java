package ui;

import services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private UserService userService;

    public LoginScreen() {
        super("Login");
        userService = new UserService();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Signup");

        panel.add(loginButton);
        panel.add(signupButton);

        add(panel, BorderLayout.CENTER);

        // Actions
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                int userId = userService.login(email, password);
                if (userId != -1) {
                    JOptionPane.showMessageDialog(LoginScreen.this, "Login successful!");
                    dispose();
                    new Dashboard(userId).setVisible(true);  // Create this class next
                } else {
                    JOptionPane.showMessageDialog(LoginScreen.this, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        signupButton.addActionListener(e -> {
            dispose();
            new SignupScreen().setVisible(true);  // Create this class next
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}