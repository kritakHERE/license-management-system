class Application {
    private String applicationID;
    private String userID;
    private String requestedCategory;
    private String status; // Pending, Approved, Rejected

    public Application(String applicationID, String userID, String requestedCategory) {
        this.applicationID = applicationID;
        this.userID = userID;
        this.requestedCategory = requestedCategory;
        this.status = "Pending";
    }

    public void approve() { status = "Approved"; }
    public void reject() { status = "Rejected"; }
    public String getStatus() { return status; }
    public String getApplicationID() { return applicationID; }
    public String getUserID() { return userID; }
    public String getRequestedCategory() { return requestedCategory; }

    @Override
    public String toString() {
        return "ApplicationID: " + applicationID + ", UserID: " + userID + ", Category: " + requestedCategory + ", Status: " + status;
    }
}

