import java.util.HashMap;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class UserManager {
    private HashMap<String, User> users = new HashMap<>();
    private HashSet<String> citizenshipNumbers = new HashSet<>();
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public void registerUser(Scanner scanner) {
        System.out.println("\n===== USER REGISTRATION =====");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        if (!isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }
        if (users.containsKey(email)) {
            System.out.println("Email already registered.");
            return;
        }
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Citizenship Number: ");
        String citizenshipNumber = scanner.nextLine();
        if (citizenshipNumbers.contains(citizenshipNumber)) {
            System.out.println("Citizenship number already registered.");
            return;
        }
        System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
        LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());
        if (Period.between(dateOfBirth, LocalDate.now()).getYears() < 18) {
            System.out.println("User must be at least 18 years old to apply for a license.");
            return;
        }
        System.out.print("Select Role (1: Normal User, 2: Admin): ");
        int role = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String userID = "U" + (users.size() + 1); // Generate unique ID
        User newUser;

        if (role == 1) {
            newUser = new NormalUser(userID, name, email, password, citizenshipNumber, dateOfBirth);
        } else if (role == 2) {
            newUser = new Admin(userID, name, email, password, citizenshipNumber, dateOfBirth);
        } else {
            System.out.println("Invalid role selection!");
            return;
        }

        users.put(email, newUser);
        citizenshipNumbers.add(citizenshipNumber);
        System.out.println("User registered successfully! User ID: " + userID);
    }

    public User loginUser(Scanner scanner) {
        System.out.println("\n===== USER LOGIN =====");
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        if (!isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return null;
        }
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

    private boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public User getUserByID(String userID) {
        for (User user : users.values()) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }
        return null;
    }
}
