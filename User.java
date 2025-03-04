
abstract class User {
    protected String userID;
    protected String name;
    protected String email;
    protected String password;
    protected String citizenshipNumber;

    public User(String userID, String name, String email, String password, String citizenshipNumber) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.citizenshipNumber = citizenshipNumber;
    }

    public String getUserID() { return userID; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public abstract void displayDashboard();
}
