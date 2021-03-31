package entities;

public class Employee {
    private final int id;
    private static int nrEmployees;
    protected String description;
    protected String occupation;
    protected String firstName;
    protected String lastName;

    {
        nrEmployees ++;
    }
    public Employee(String description, String ocupation, String firstName, String lastName)
    {
        this.id = nrEmployees;
        this.description = description;
        this.occupation = ocupation;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static int getNrEmployees() {
        return nrEmployees;
    }

    public static void setNrEmployees(int nrEmployees) {
        Employee.nrEmployees = nrEmployees;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + "("+ occupation +"')";
    }
}
