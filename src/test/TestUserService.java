package test;

import models.User;
import services.UserService;
import java.util.List;

public class TestUserService {
    public static void main(String[] args) {
        UserService userService = UserService.getInstance();

        userService.register("testuser", "testuser@example.com", "testpassword");

        List<User> users = userService.getUsers();

        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail());
        }
    }
}
