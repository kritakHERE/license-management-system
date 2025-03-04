import java.time.LocalDate;

class License {
    private String licenseID;
    private String userID;
    private String category;
    private LocalDate issueDate;
    private LocalDate expiryDate;

    public License(String licenseID, String userID, String category) {
        this.licenseID = licenseID;
        this.userID = userID;
        this.category = category;
        this.issueDate = LocalDate.now();
        this.expiryDate = issueDate.plusYears(5); // License valid for 5 years
    }

    public String getLicenseID() { return licenseID; }
    public String getUserID() { return userID; }
    public String getCategory() { return category; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getExpiryDate() { return expiryDate; }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public String toString() {
        return "LicenseID: " + licenseID + ", Category: " + category + ", Issue Date: " + issueDate + ", Expiry Date: " + expiryDate;
    }
}
