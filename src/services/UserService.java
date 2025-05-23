package services;

import models.User;

public class UserService {
    public boolean login(User user) {
        // TODO: Check username/password from DB
        System.out.println("Login attempted for user: " + user.getUsername());
        return true;
    }

    public boolean register(User user) {
        // TODO: Save user in DB
        System.out.println("Registering user: " + user.getUsername());
        return true;
    }
}