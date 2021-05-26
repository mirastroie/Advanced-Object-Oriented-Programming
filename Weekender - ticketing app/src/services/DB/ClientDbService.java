package services.DB;

import config.DatabaseConfig;
import entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDbService {
    private static ClientDbService service;
    private ClientDbService(){

    }
    public static ClientDbService getUserDbService()
    {
        if(service == null)
            service = new ClientDbService();
        return service;
    }
    public void addClient(Client client)
    {
        String addClient = "INSERT INTO clients values(?,?,?,?,?,?)";
        Connection connection = DatabaseConfig.getDbConnection();
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(addClient);
            preparedStatement.setInt(1,client.getId());
            preparedStatement.setString(2, client.getPhoneNumber());
            preparedStatement.setString(3,client.getUsername());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5,client.getFullname());
            preparedStatement.setString(6, client.getPassword());
            preparedStatement.executeUpdate();

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public Optional<Client> getById(int id)
    {
        String getByIdCmd = "SELECT * FROM CLIENTS c WHERE c.id = ?";
        Connection connection = DatabaseConfig.getDbConnection();
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(getByIdCmd);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return toClient(resultSet);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    public List<Client> show()
    {
        String showSql = "SELECT * FROM CLIENTS";
        Connection connection = DatabaseConfig.getDbConnection();
        List<Client> clients = new ArrayList<>();
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(showSql);
            // ResultSet starts out pointing before the first record
            Optional<Client> client = toClient(resultSet);
            while(client.isPresent())
            {
               clients.add(client.get());
               client = toClient(resultSet);
            }

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return clients;
    }
    public void updateClient(Client newClient, int id)
    {
        Connection connection = DatabaseConfig.getDbConnection();
        if(newClient.getId() == id)
        {
            Optional<Client> client = getById(id);
            if(client.isPresent())
            {
                 String updateSql = "UPDATE clients SET phoneNumber = ?," +
                         "username = ?, email = ?, fullName = ?, password = ? " +
                         "WHERE id = ?";
                 try {
                     PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
                     preparedStatement.setString(1, newClient.getPhoneNumber());
                     preparedStatement.setString(2, newClient.getUsername());
                     preparedStatement.setString(3, newClient.getEmail());
                     preparedStatement.setString(4, newClient.getFullname());
                     preparedStatement.setString(5, newClient.getPassword());
                     preparedStatement.setInt(6, newClient.getId());
                     preparedStatement.executeUpdate();

                 }catch (SQLException e)
                 {
                     e.printStackTrace();
                 }
            }
            else
                System.out.println("The client could not be found!");
        }
        else
            System.out.println("You can't update this client!");
    }
    public void delete(int id)
    {
          Optional<Client> client = getById(id);
          if(client.isPresent())
          {
              try {
                  Connection connection = DatabaseConfig.getDbConnection();
                  String deleteSql = "DELETE FROM clients WHERE id = ?";
                  PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
                  preparedStatement.setInt(1, id);
                  preparedStatement.executeUpdate();

              }catch(SQLException e)
              {
                  e.printStackTrace();
              }
          }
          else
              System.out.println("Nu exista niciun user cu id-ul dat!");
    }
    private Optional<Client> toClient(ResultSet resultSet) throws  SQLException
    {
        if(resultSet.next()){
            int id = resultSet.getInt(1);
            String phoneNumber = resultSet.getString(2);
            String username = resultSet.getString(3);
            String email = resultSet.getString(4);
            String fullname = resultSet.getString(5);
            String password = resultSet.getString(6);
            //String ordersIds = resultSet.getString(7);
            //boolean bool = resultSet.wasNull();
            //if(bool)
                 return Optional.of(new Client(id, phoneNumber, username,email,fullname,password));
            //else {
                //trebuie preluate valorile din Orders
                //return Optional.of(new Client(id, phoneNumber, username, email, fullname, password, ParseColumnArray.unparseColumn(ordersIds)));
            //}
        }
        return Optional.empty();
    }

}
