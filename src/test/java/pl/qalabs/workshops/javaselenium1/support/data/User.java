package pl.qalabs.workshops.javaselenium1.support.data;

public class User {

    private final String firstName;
    private final String lastName;
    private final String gender;
    private final int age;
    private final String email;

    User(String firstName, String lastName, String gender, int age, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getGender() {
        return gender;
    }

    public boolean isMale() {
        return "M".equalsIgnoreCase(gender);
    }

    public boolean isFemale() {
        return "F".equalsIgnoreCase(gender);
    }

    public int getAge() {
        return age;
    }

    public String getAgeAsString() {
        return age + "";
    }

    public String getEmail() {
        return email;
    }
}
