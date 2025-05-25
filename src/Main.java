package main;

import services.UserService;
import ui.LoginScreen;

public class Main {
    public static void main(String[] args) {
        UserService userService = UserService.getInstance();  // Singleton
        new LoginScreen(userService).setVisible(true);        // Pass service
    }
}