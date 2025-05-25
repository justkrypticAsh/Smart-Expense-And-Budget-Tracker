# Smart Expense and Budget Tracker

A desktop-based Java application that helps users manage their personal finances by tracking daily expenses and monitoring monthly budgets. Built using Java Swing for GUI and MySQL for backend database.

---

## **Features**
- User Registration and Login
- Add, Edit, and Delete Expenses
- Category-wise Budget Tracking
- Visual Pie Chart Summary
- MySQL Database Integration
- Clean and User-Friendly GUI using Java Swing

---

## **Tech Stack**
- **Language:** Java
- **GUI:** Java Swing
- **Database:** MySQL
- **JDBC** for connectivity
- **IDE:** VS Code (recommended)

---

## **Project Structure**
```
Smart-Expense-And-Budget-Tracker/
├── src/
│   ├── database/           # DBConnection and Initializer
│   ├── models/             # User, Expense, Category models
│   ├── services/           # Business logic and services
│   ├── test/               # Test classes
│   └── Main.java           # Entry point for the application
├── lib/                    # External libraries (e.g., MySQL connector)
├── README.md               # Project documentation
├── .gitignore              # Ignored files config
```

---

## **Database Setup**

1. **Install MySQL** and ensure it’s running.
2. Set your MySQL credentials in:  
   `src/database/DBConnection.java`

```java
private static final String URL = "jdbc:mysql://localhost:3306/expense_db";
private static final String USER = "root";
private static final String PASSWORD = "lko567u";
```

3. Run this file to create the database and tables:  
   `src/database/DatabaseInitializer.java`

---

## **Running the Project**

### Option 1: Run from Source
1. Clone the repository:
   ```bash
   git clone https://github.com/justkrypticAsh/Smart-Expense-And-Budget-Tracker.git
   ```
2. Open in VS Code or any Java IDE.
3. Ensure Java and MySQL are installed.
4. Run `Main.java` to launch the app.

### Option 2: Compile and Run via Terminal
```bash
javac -d out src/**/*.java
java -cp out Main
```

---

## **Screenshots**

*(Add screenshots of your login screen, dashboard, pie chart, etc. here if available)*

---

## **Contributors**

- Ashish Kumar Sharma  
- Sneha Kumari  
- Prachi Mishra  
- Abhiratna Bhushan Chakravarti

---

## **License**
This project is for academic purposes under Galgotias University.
