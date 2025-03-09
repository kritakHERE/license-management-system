import java.util.Scanner;
import java.time.LocalDate;

class Admin extends User {
    public Admin(String userID, String name, String email, String password, String citizenshipNumber, LocalDate dateOfBirth) {
        super(userID, name, email, password, citizenshipNumber, dateOfBirth);
    }

    public void adminDashboard(Scanner scanner) {
        while (true) {
            System.out.println("\n===== ADMIN DASHBOARD =====");
            System.out.println("1. View Pending Applications");
            System.out.println("2. Approve/Reject Application");
            System.out.println("3. Generate Reports");
            System.out.println("4. Renew License");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    viewPendingApplications();
                    break;
                case 2:
                    processApplication(scanner);
                    break;
                case 3:
                    generateReports();
                    break;
                case 4:
                    renew(scanner);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public void viewPendingApplications() {
        System.out.println("Viewing pending applications...");
        for (Application app : ApplicationManager.getPendingApplications()) {
            System.out.println(app);
        }
    }

    public void processApplication(Scanner scanner) {
        System.out.print("Enter application ID to approve(a,A)/reject(r,R): ");
        String appId = scanner.nextLine();
        System.out.print("Approve or Reject (A/R): ");
        String decision = scanner.nextLine().toUpperCase();

        if (decision.equals("A")) {
            ApplicationManager.approveApplication(appId, this.userID);
        } else if (decision.equals("R")) {
            ApplicationManager.rejectApplication(appId);
        } else {
            System.out.println("Invalid decision. Please enter 'A' to approve or 'R' to reject.");
        }
    }

    public void generateReports() {
        System.out.println("Generating reports...");
        // Example report generation logic
        System.out.println("Total Applications: " + ApplicationManager.getPendingApplications().size());
        System.out.println("Total Licenses Issued: " + ApplicationManager.getLicenseByUser(this.userID));
    }

    public void renew(Scanner scanner) {
        System.out.print("Enter user ID to renew license: ");
        String userID = scanner.nextLine();
        NormalUser user = (NormalUser) ApplicationManager.getUserByID(userID);
        if (user != null) {
            user.renew();
        } else {
            System.out.println("User not found.");
        }
    }

    @Override
    public void displayDashboard() {
        System.out.println("Welcome to Admin Dashboard!");
    }
}
