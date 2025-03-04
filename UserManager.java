import java.util.HashMap;
import java.util.Scanner;

class UserManager {
    private HashMap<String, User> users = new HashMap<>();

    public void registerUser(Scanner scanner) {
        System.out.println("\n===== USER REGISTRATION =====");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        if (users.containsKey(email)) {
            System.out.println("Email already registered.");
            return;
        }
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Citizenship Number: ");
        String citizenshipNumber = scanner.nextLine();
        System.out.print("Select Role (1: Normal User, 2: Admin): ");
        int role = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String userID = "U" + (users.size() + 1); // Generate unique ID
        User newUser;

        if (role == 1) {
            newUser = new NormalUser(userID, name, email, password, citizenshipNumber);
        } else if (role == 2) {
            newUser = new Admin(userID, name, email, password, citizenshipNumber);
        } else {
            System.out.println("Invalid role selection!");
            return;
        }

        users.put(email, newUser);
        System.out.println("User registered successfully! User ID: " + userID);
    }

    public User loginUser(Scanner scanner) {
        System.out.println("\n===== USER LOGIN =====");
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = users.get(email);

        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful! Welcome, " + user.name);
            return user;
        } else {
            System.out.println("Invalid email or password.");
            return null;
        }
    }
}
