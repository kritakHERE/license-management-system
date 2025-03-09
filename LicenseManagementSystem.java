import java.util.Scanner;

public class LicenseManagementSystem {
    // ANSI escape codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();  // Manages user data

        while (true) {
            System.out.println(CYAN + "\n===== LICENSE MANAGEMENT SYSTEM =====" + RESET);
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            try {
                switch (choice) {
                    case 1:
                        userManager.registerUser(scanner);
                        break;
                    case 2:
                        User user = userManager.loginUser(scanner);
                        if (user != null) {
                            if (user instanceof Admin) {
                                ((Admin) user).adminDashboard(scanner);
                            } else {
                                ((NormalUser) user).userDashboard(scanner);
                            }
                        } else {
                            System.out.println(YELLOW + "Returning to main menu..." + RESET);
                        }
                        break;
                    case 3:
                        System.out.println(GREEN + "Exiting the system. Goodbye!" + RESET);
                        scanner.close();
                        return;
                    default:
                        System.out.println(RED + "Invalid choice. Please try again." + RESET);
                }
            } catch (Exception e) {
                System.out.println(RED + "An error occurred: " + e.getMessage() + RESET);
                scanner.nextLine(); // Consume newline
            }
        }
    }
}
