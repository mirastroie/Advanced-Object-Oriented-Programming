package validators;

import entities.Employee;
import util.Error;

public class EmployeeValidator {


    public boolean validateFirstName(Employee employee)
    {
        String regex = "[A-Z][a-z]{2,}([-][A-Z][a-z]{2,}){0,1}";
        return employee.getFirstName().matches(regex);
    }
    public boolean validateLastName(Employee employee)
    {
        String regex = "[A-Z][a-z]{2,}";
        return employee.getLastName().matches(regex);
    }
    public String validateEmployee(Employee employee)
    {
        StringBuilder result = new StringBuilder();

        if(!validateFirstName(employee))
            result.append(Error.INVALID_FIRSTNAME + ", ");
        if(!validateLastName(employee))
            result.append(Error.INVALID_LASTNAME +  ", ");

        return result.toString();

    }
}
