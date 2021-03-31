package services;

import entities.Employee;
import util.MyException;
import validators.EmployeeValidator;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    private List<Employee> employees;
    private static EmployeeService employeeService;
    private EmployeeService(){
        employees = new ArrayList<Employee>();
    }
    public static EmployeeService getEmployeeService()
    {
        if(employeeService == null)
            employeeService = new EmployeeService();
        return employeeService;
    }
    public void addEmployee(Employee employee)
    {
        try {

            EmployeeValidator validator = new EmployeeValidator();
            String errors = validator.validateEmployee(employee);
            if (errors.length() > 0) {
               throw new MyException(errors);
            }
            if (!employees.contains(employee))
                employees.add(employee);
        }
        catch (MyException e)
        {
            System.out.println("The employee can't be added: " + e);
        }
    }
    public void showEmployees()
    {
        System.out.println("\nTHE EMPLOYEES ARE: ");
        for(int i = 0; i < employees.size(); i ++)
            System.out.println((i+1) + ". " + employees.get(i).toString());
        System.out.println();
    }
}
