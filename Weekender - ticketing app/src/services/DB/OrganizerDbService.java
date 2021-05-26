package services.DB;

import config.DatabaseConfig;
import entities.Organizer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrganizerDbService {
    private static OrganizerDbService service;
    private OrganizerDbService(){

    }
    public static OrganizerDbService getOrganizerDbService()
    {
        if(service == null)
            service = new OrganizerDbService();
        return service;
    }
    public void addOrganizer(Organizer Organizer)
    {
        String addOrganizer = "INSERT INTO organizers(id,description,fullName,username, password,email) values(?,?,?,?,?,?)";
        Connection connection = DatabaseConfig.getDbConnection();
        try
        {
            // Integer id, String description, String fullname, String username, String password, String email
            PreparedStatement preparedStatement = connection.prepareStatement(addOrganizer);
            preparedStatement.setInt(1,Organizer.getId());
            preparedStatement.setString(2, Organizer.getDescription());
            preparedStatement.setString(3,Organizer.getFullname());
            preparedStatement.setString(4, Organizer.getUsername());
            preparedStatement.setString(5,Organizer.getPassword());
            preparedStatement.setString(6, Organizer.getEmail());
            preparedStatement.executeUpdate();

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public Optional<Organizer> getById(int id)
    {
        String getByIdCmd = "SELECT * FROM organizers c WHERE c.id = ?";
        Connection connection = DatabaseConfig.getDbConnection();
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(getByIdCmd);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return toOrganizer(resultSet);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    public List<Organizer> show()
    {
        String showSql = "SELECT * FROM organizers";
        Connection connection = DatabaseConfig.getDbConnection();
        List<Organizer> Organizers = new ArrayList<>();
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(showSql);
            // ResultSet starts out pointing before the first record
            Optional<Organizer> Organizer = toOrganizer(resultSet);
            while(Organizer.isPresent())
            {
                Organizers.add(Organizer.get());
                Organizer = toOrganizer(resultSet);
            }

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return Organizers;
    }
    public void updateOrganizer(Organizer newOrganizer, int id)
    {
        Connection connection = DatabaseConfig.getDbConnection();
        if(newOrganizer.getId() == id)
        {
            Optional<Organizer> organizer = getById(id);
            if(organizer.isPresent())
            {
                String updateSql = "UPDATE organizers SET description = ?," +
                        "fullName = ?, username = ?, password = ?, email = ?" +
                        "WHERE id = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
                    preparedStatement.setInt(6,newOrganizer.getId());
                    preparedStatement.setString(1, newOrganizer.getDescription());
                    preparedStatement.setString(2,newOrganizer.getFullname());
                    preparedStatement.setString(3, newOrganizer.getUsername());
                    preparedStatement.setString(4, newOrganizer.getPassword());
                    preparedStatement.setString(5, newOrganizer.getEmail());
                    preparedStatement.executeUpdate();

                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            else
                System.out.println("The organizer could not be found!");
        }
        else
            System.out.println("You can't update this organizer!");
    }
    public void updateDescription(String newDescription, int id)
    {
        Connection connection = DatabaseConfig.getDbConnection();
        Optional<Organizer> Organizer = getById(id);
        if(Organizer.isPresent())
        {
            try {
                CallableStatement callableStatement = connection.prepareCall("{? = call updateOrganizerDescription(?,?)}");
                callableStatement.registerOutParameter(1, Types.VARCHAR);
                callableStatement.setInt(2,id);
                callableStatement.setString(3, newDescription);
                callableStatement.execute();
                System.out.println("The description of the organizer was changed from " +  callableStatement.getString(1) +
                        " to " + newDescription);

            }catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
            System.out.println("The Organizer could not be found!");

    }
    public void delete(int id)
    {
        Optional<Organizer> Organizer = getById(id);
        if(Organizer.isPresent())
        {
            try {
                Connection connection = DatabaseConfig.getDbConnection();
                String deleteSql = "DELETE FROM Organizers WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();

            }catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
            System.out.println("Nu exista niciun organizer cu id-ul dat!");
    }
    private Optional<Organizer> toOrganizer(ResultSet resultSet) throws  SQLException
    {
        if(resultSet.next()){

            int id = resultSet.getInt(1);
            String description = resultSet.getString(2);
            String fullname = resultSet.getString(3);
            String username = resultSet.getString(4);
            String password = resultSet.getString(5);
            String email = resultSet.getString(6);
            return Optional.of(new Organizer(id, description, fullname, username, password, email));
        }
        return Optional.empty();
    }

}
