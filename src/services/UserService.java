package services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import models.User;

public class UserService implements Serializable {
    private static final long serialVersionUID = 1L;
    private static UserService instance;
    private List<User> users;
    private int nextId;
    private static final String DATA_FILE = "users.dat";

    private UserService() {
        users = new ArrayList<>();
        nextId = 1;
        loadFromFile();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public boolean register(String name, String email, String password) {
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                return false;
            }
        }
        User newUser = new User(nextId++, name, email, password, 0.0);
        users.add(newUser);
        saveToFile();
        return true;
    }

    public int login(String email, String password) {
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return u.getId();
            }
        }
        return -1;
    }

    public List<User> getUsers() {
        return users;
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(users);
            oos.writeInt(nextId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            users = (List<User>) ois.readObject();
            nextId = ois.readInt();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
