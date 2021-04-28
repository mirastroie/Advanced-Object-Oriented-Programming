package services.IO;
import entities.Employee;
import java.util.List;

public class EmployeeIOService extends AbstractIOService<Employee> {
    private static EmployeeIOService service;

    public static EmployeeIOService getEmployeeIOService()
    {
        if(service == null) {
            service = new EmployeeIOService();
        }
        return service;
    }
    public List<Employee> load()
    {
        String FILE_NAME = "data/employees.csv";
        return super.load(FILE_NAME);
    }
    public void save(List<Employee> s)
    {
        String FILE_NAME = "data/employees.csv";
        String HEADER_FILE = "Id, Description, Occupation, First name, Last name";
        super.save(s,FILE_NAME, HEADER_FILE);
    }
    public Employee parse(List<String> entry)
    {
        return new Employee(Integer.parseInt(entry.get(0)),entry.get(1).trim(),entry.get(2).trim(),entry.get(3).trim(),
                entry.get(4).trim());
    }
    public String unparse(Employee employee)
    {
        return employee.getId() + ", " + "\"" + employee.getDescription() + "\", " + employee.getOccupation() + ", " +
                employee.getFirstName() + ", " + employee.getLastName();
    }
}
