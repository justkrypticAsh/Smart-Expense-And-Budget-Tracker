import java.util.Scanner;
import models.User;
import services.UserService;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Smart Expense and Budget Tracker!");

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            if (choice == 1) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                User newUser = new User(0, username, password);
                if (userService.register(newUser)) {
                    System.out.println("Registration successful!");
                } else {
                    System.out.println("Registration failed.");
                }

            } else if (choice == 2) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                User user = new User(0, username, password);
                if (userService.login(user)) {
                    System.out.println("Login successful!");
                    // aage ka dashboard ya menu dikha sakte ho
                    break;
                } else {
                    System.out.println("Invalid username or password.");
                }
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}