package ui;

import javax.swing.*;
import java.awt.event.*;
import services.UserService;

public class SignupScreen extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton signupButton;

    private UserService userService;

    public SignupScreen(UserService userService) {
        this.userService = userService;

        setTitle("Signup");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 10, 80, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 10, 160, 25);
        add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 40, 80, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(100, 40, 160, 25);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 70, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 70, 160, 25);
        add(passwordField);

        signupButton = new JButton("Sign Up");
        signupButton.setBounds(100, 110, 100, 25);
        add(signupButton);

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());

                boolean success = userService.register(name, email, password);
                if (success) {
                    JOptionPane.showMessageDialog(null, "User registered successfully!");
                    dispose();
                    new LoginScreen(userService).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Registration failed. Email may already be used.");
                }
            }
        });
    }
}
