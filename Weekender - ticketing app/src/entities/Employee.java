package entities;
import java.util.Objects;

public class Employee implements Comparable<Employee>,Cloneable {
    private int id;
    private static int max = 1;
    private static int nrEmployees;
    protected String description;
    protected String occupation;
    protected String firstName;
    protected String lastName;

    {
        nrEmployees ++;
    }
    public Employee(String description, String occupation, String firstName, String lastName)
    {
        this.id = max ++;
        this.description = description;
        this.occupation = occupation;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Employee(Integer id, String description, String occupation, String firstName, String lastName)
    {
        this(description,occupation,firstName,lastName);
        this.id = id;
        max = Math.max(id,max - 1) + 1;
    }

    public int getId() {
        return id;
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
        return firstName + " " + lastName + "("+ occupation +")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(description, employee.description) && Objects.equals(occupation, employee.occupation) &&
                Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, occupation, firstName, lastName);
    }

    @Override
    public int compareTo(Employee employee)
    {
        if (this.firstName.equals(employee.firstName))
            return this.lastName.compareTo(employee.getLastName());
        else
            return this.firstName.compareTo(employee.getFirstName());
    }
    @Override
    protected Object clone() {
        Employee employee = null;
        try{
            employee = (Employee) super.clone();
        }catch (CloneNotSupportedException e)
        {
            employee = new Employee(
                    this.getId(), this.getDescription(), this.getOccupation(),
                    this.getFirstName(),this.getLastName());
        }
        return employee;
    }
}
