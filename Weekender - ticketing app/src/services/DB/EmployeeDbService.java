package services.DB;

import config.DatabaseConfig;
import entities.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDbService {
    private static EmployeeDbService service;
    private EmployeeDbService(){

    }
    public static EmployeeDbService getEmployeeDbService()
    {
        if(service == null)
            service = new EmployeeDbService();
        return service;
    }
    public void addEmployee(Employee Employee)
    {
        String addEmployee = "INSERT INTO Employees values(?,?,?,?,?)";
        Connection connection = DatabaseConfig.getDbConnection();
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(addEmployee);
            preparedStatement.setInt(1,Employee.getId());
            preparedStatement.setString(2, Employee.getDescription());
            preparedStatement.setString(3,Employee.getOccupation());
            preparedStatement.setString(4, Employee.getFirstName());
            preparedStatement.setString(5,Employee.getLastName());
            preparedStatement.executeUpdate();

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public Optional<Employee> getById(int id)
    {
        String getByIdCmd = "SELECT * FROM Employees c WHERE c.id = ?";
        Connection connection = DatabaseConfig.getDbConnection();
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(getByIdCmd);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return toEmployee(resultSet);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    public List<Employee> showByEvent(int id)
    {
        String showSql = "SELECT distinct e.id, e.description, e.occupation, e.firstName, e.lastName\n" +
                "FROM Employees e JOIN eventsEmployees ee \n" +
                "on ee.employee_id = e.id \n" +
                "where event_id = ?";
        Connection connection = DatabaseConfig.getDbConnection();
        List<Employee> Employees = new ArrayList<>();
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(showSql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            // ResultSet starts out pointing before the first record
            Optional<Employee> Employee = toEmployee(resultSet);
            while(Employee.isPresent())
            {
                Employees.add(Employee.get());
                Employee = toEmployee(resultSet);
            }

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return Employees;
    }
    public void updateEmployee(Employee newEmployee, int id)
    {
        Connection connection = DatabaseConfig.getDbConnection();
        if(newEmployee.getId() == id)
        {
            Optional<Employee> employee = getById(id);
            if(employee.isPresent())
            {
                String updateSql = "UPDATE employees SET description = ?," +
                        "occupation = ?, firstName = ?, lastName = ?" +
                        "WHERE id = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
                    preparedStatement.setInt(5, newEmployee.getId());
                    preparedStatement.setString(1, newEmployee.getDescription());
                    preparedStatement.setString(2,newEmployee.getOccupation());
                    preparedStatement.setString(3, newEmployee.getFirstName());
                    preparedStatement.setString(4,newEmployee.getLastName());
                    preparedStatement.executeUpdate();

                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            else
                System.out.println("The employee could not be found!");
        }
        else
            System.out.println("You can't update this employee!");
    }
    public List<Employee> show()
    {
        String showSql = "SELECT * FROM Employees";
        Connection connection = DatabaseConfig.getDbConnection();
        List<Employee> Employees = new ArrayList<>();
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(showSql);
            // ResultSet starts out pointing before the first record
            Optional<Employee> Employee = toEmployee(resultSet);
            while(Employee.isPresent())
            {
                Employees.add(Employee.get());
                Employee = toEmployee(resultSet);
            }

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return Employees;
    }

    public void delete(int id)
    {
        Optional<Employee> Employee = getById(id);
        if(Employee.isPresent())
        {
            try {
                Connection connection = DatabaseConfig.getDbConnection();
                String deleteSql = "DELETE FROM Employees WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();

            }catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
            System.out.println("Nu exista niciun angajat cu id-ul dat!");
    }
    private Optional<Employee> toEmployee(ResultSet resultSet) throws  SQLException
    {
        if(resultSet.next()){

            int id = resultSet.getInt(1);
            String description = resultSet.getString(2);
            String occupation = resultSet.getString(3);
            String firstName = resultSet.getString(4);
            String lastName = resultSet.getString(5);
            return Optional.of(new Employee(id, description, occupation, firstName,lastName));
        }
        return Optional.empty();
    }

}
