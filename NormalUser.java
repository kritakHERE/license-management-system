import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;

class NormalUser extends User {
    // ANSI escape codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";

    public NormalUser(String userID, String name, String email, String password, String citizenshipNumber, LocalDate dateOfBirth) {
        super(userID, name, email, password, citizenshipNumber, dateOfBirth);
    }

    public void userDashboard(Scanner scanner) {
        while (true) {
            System.out.println(CYAN + "\n===== USER DASHBOARD =====" + RESET);
            System.out.println("1. Apply for License");
            System.out.println("2. Check Application Status");
            System.out.println("3. Renew License");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    applyForLicense(scanner);
                    break;
                case 2:
                    checkStatus();
                    break;
                case 3:
                    renew();
                    break;
                case 4:
                    System.out.println(GREEN + "Logging out..." + RESET);
                    return;
                default:
                    System.out.println(RED + "Invalid choice. Try again." + RESET);
            }
        }
    }

    public void applyForLicense(Scanner scanner) {
        System.out.println(CYAN + "Applying for a license..." + RESET);
        System.out.print("Enter requested category (A,B,C,D,E,F): ");
        String category = scanner.nextLine().toUpperCase();

        if (!isEligibleForCategory(category)) {
            System.out.println(RED + "You are not eligible to apply for this category." + RESET);
            return;
        }

        String applicationID = "AP" + (ApplicationManager.getPendingApplications().size() + 1);
        Application application = new Application(applicationID, this.userID, category);
        ApplicationManager.submitApplication(application);

        // Auto-approve if applying for a lower category
        License currentLicense = ApplicationManager.getLicenseByUser(this.userID);
        if (currentLicense != null && category.charAt(0) > currentLicense.getCategory().charAt(0)) {
            ApplicationManager.approveApplication(applicationID, "System");
        }
    }

    private boolean isEligibleForCategory(String category) {
        License currentLicense = ApplicationManager.getLicenseByUser(this.userID);
        if (currentLicense == null) {
            return true; // No license, eligible for any category
        }
        char currentCategory = currentLicense.getCategory().charAt(0);
        char requestedCategory = category.charAt(0);
        return requestedCategory >= currentCategory; // Eligible for same or lower category
    }

    public void renew() {
        System.out.println(CYAN + "Renewing license..." + RESET);
        License currentLicense = ApplicationManager.getLicenseByUser(this.userID);
        if (currentLicense != null && currentLicense.isExpired()) {
            String applicationID = "AP" + (ApplicationManager.getPendingApplications().size() + 1);
            Application application = new Application(applicationID, this.userID, currentLicense.getCategory());
            ApplicationManager.submitApplication(application);
        } else {
            System.out.println(RED + "No license to renew or license is not expired." + RESET);
        }
    }

    public void checkStatus() {
        System.out.println(CYAN + "Checking application status..." + RESET);
        List<Application> applications = ApplicationManager.getApplicationsByUser(this.userID);
        if (applications.isEmpty()) {
            System.out.println(YELLOW + "No applications found." + RESET);
        } else {
            for (Application application : applications) {
                System.out.println(application);
            }
        }
    }

    @Override
    public void displayDashboard() {
        System.out.println(CYAN + "Welcome to Normal User Dashboard!" + RESET);
    }
}
