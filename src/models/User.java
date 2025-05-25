package models;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String email;
    private String password;
    private double monthlyBudget;

    public User(int id, String name, String email, String password, double monthlyBudget) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.monthlyBudget = monthlyBudget;
    }

    // getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public double getMonthlyBudget() { return monthlyBudget; }
    public void setMonthlyBudget(double monthlyBudget) { this.monthlyBudget = monthlyBudget; }
}
