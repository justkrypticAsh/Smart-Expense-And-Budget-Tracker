package models;

import java.util.Date;

public class Expense {
    private int id;
    private int userId;
    private double amount;
    private String category;
    private Date date;
    private String note;

    public Expense(int id, int userId, double amount, String category, Date date, String note) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.note = note;
    }

    public Expense(int userId, double amount, String category, Date date, String note) {
        this.id = -1;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }
}
