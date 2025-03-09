import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;

class NormalUser extends User {
    public NormalUser(String userID, String name, String email, String password, String citizenshipNumber, LocalDate dateOfBirth) {
        super(userID, name, email, password, citizenshipNumber, dateOfBirth);
    }

    public void userDashboard(Scanner scanner) {
        while (true) {
            System.out.println("\n===== USER DASHBOARD =====");
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
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public void applyForLicense(Scanner scanner) {
        System.out.println("Applying for a license...");
        System.out.print("Enter requested category (A,B,C,D,E,F): ");
        String category = scanner.nextLine().toUpperCase();

        if (!isEligibleForCategory(category)) {
            System.out.println("You are not eligible to apply for this category.");
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
        System.out.println("Renewing license...");
        License currentLicense = ApplicationManager.getLicenseByUser(this.userID);
        if (currentLicense != null && currentLicense.isExpired()) {
            String applicationID = "AP" + (ApplicationManager.getPendingApplications().size() + 1);
            Application application = new Application(applicationID, this.userID, currentLicense.getCategory());
            ApplicationManager.submitApplication(application);
        } else {
            System.out.println("No license to renew or license is not expired.");
        }
    }

    public void checkStatus() {
        System.out.println("Checking application status...");
        List<Application> applications = ApplicationManager.getApplicationsByUser(this.userID);
        if (applications.isEmpty()) {
            System.out.println("No applications found.");
        } else {
            for (Application application : applications) {
                System.out.println(application);
            }
        }
    }

    @Override
    public void displayDashboard() {
        System.out.println("Welcome to Normal User Dashboard!");
    }
}
