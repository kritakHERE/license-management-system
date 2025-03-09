import java.time.LocalDate;
import java.time.Period;

abstract class User {
    protected String userID;
    protected String name;
    protected String email;
    protected String password;
    protected String citizenshipNumber;
    protected LocalDate dateOfBirth;

    public User(String userID, String name, String email, String password, String citizenshipNumber, LocalDate dateOfBirth) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.citizenshipNumber = citizenshipNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserID() { return userID; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public abstract void displayDashboard();
}
