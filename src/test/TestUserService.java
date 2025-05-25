package test;

import models.User;
import services.UserService;
import java.util.List;

public class TestUserService {
    public static void main(String[] args) {
        UserService userService = new UserService();

        // Add a new user
        User newUser = new User(0, "testuser", "testpassword");
        userService.addUser(newUser);

        // Fetch all users and print
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Username: " + user.getUsername());
        }
    }
}