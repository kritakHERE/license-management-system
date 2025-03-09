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

    // ANSI escape codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";

    public void registerUser(Scanner scanner) {
        System.out.println(CYAN + "\n===== USER REGISTRATION =====" + RESET);
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        if (!isValidEmail(email)) {
            System.out.println(RED + "Invalid email format." + RESET);
            return;
        }
        if (users.containsKey(email)) {
            System.out.println(RED + "Email already registered." + RESET);
            return;
        }
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Citizenship Number: ");
        String citizenshipNumber = scanner.nextLine();
        if (citizenshipNumbers.contains(citizenshipNumber)) {
            System.out.println(RED + "Citizenship number already registered." + RESET);
            return;
        }
        System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
        LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());
        if (Period.between(dateOfBirth, LocalDate.now()).getYears() < 18) {
            System.out.println(RED + "User must be at least 18 years old to apply for a license." + RESET);
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
            System.out.println(RED + "Invalid role selection!" + RESET);
            return;
        }

        users.put(email, newUser);
        citizenshipNumbers.add(citizenshipNumber);
        System.out.println(GREEN + "User registered successfully! User ID: " + userID + RESET);
    }

    public User loginUser(Scanner scanner) {
        System.out.println(CYAN + "\n===== USER LOGIN =====" + RESET);
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        if (!isValidEmail(email)) {
            System.out.println(RED + "Invalid email format." + RESET);
            return null;
        }
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = users.get(email);

        if (user != null && user.getPassword().equals(password)) {
            System.out.println(GREEN + "Login successful! Welcome, " + user.name + RESET);
            return user;
        } else {
            System.out.println(RED + "Invalid email or password." + RESET);
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
