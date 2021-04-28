package services;
import entities.Employee;
import services.IO.Audit;
import services.IO.EmployeeIOService;
import util.MyException;
import validators.EmployeeValidator;
import java.util.*;

public class EmployeeService {

    private TreeSet<Employee> employees;
    private static EmployeeService employeeService;
    private EmployeeService(){
        employees = new TreeSet<>(EmployeeIOService.getEmployeeIOService().load());
    }
    public void close()
    {
        EmployeeIOService.getEmployeeIOService().save(new ArrayList<>(employees));
    }
    public static EmployeeService getEmployeeService()
    {
        if(employeeService == null)
            employeeService = new EmployeeService();
        return employeeService;
    }
    public Employee getEmployeeById(int id)
    {

        Optional<Employee> employee = employees.stream().filter(elem -> elem.getId() == id).findFirst();
        return employee.orElse(null);

    }
    public List<Employee> getEmployeesByIds(List<Integer> ids)
    {
        List<Employee> employees = new ArrayList<>(List.of());
        for(Integer i: ids)
        {
            employees.add(getEmployeeById(i));
        }
        return employees;
    }
    public void addEmployee(Employee employee)
    {
        Audit.getAudit().addAction("addEmployee");
        try {

            EmployeeValidator validator = new EmployeeValidator();
            String errors = validator.validateEmployee(employee);
            if (errors.length() > 0) {
               throw new MyException(errors);
            }
            employees.add(employee);
        }
        catch (MyException e)
        {
            System.out.println("The employee can't be added: " + e);
        }
    }
    public void showEmployees()
    {
        Audit.getAudit().addAction("showEmployees");
        System.out.println("\nTHE EMPLOYEES ARE: ");
        int i = 1;
        for (Employee employee : employees) {
            System.out.println(i + ". " + employee);
            i += 1;
        }
        System.out.println();
    }
}
