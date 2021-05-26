package services.DB;

import config.DatabaseConfig;
import entities.Venue;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VenueDbService {
    private static VenueDbService service;
    private VenueDbService(){

    }
    public static VenueDbService getVenueDbService()
    {
        if(service == null)
            service = new VenueDbService();
        return service;
    }
    public void addVenue(Venue Venue)
    {
        String addVenue = "INSERT INTO venues values(?,?,?,?,?,?,?,?,?)";
        Connection connection = DatabaseConfig.getDbConnection();
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(addVenue);
            preparedStatement.setInt(1,Venue.getId());
            preparedStatement.setString(2, Venue.getName());
            preparedStatement.setString(3,Venue.getDescription());
            preparedStatement.setInt(4, Venue.getCapacity());
            preparedStatement.setString(5,Venue.getAddress());
            preparedStatement.setString(6, Venue.getRegion());
            preparedStatement.setString(7, Venue.getZIP());
            preparedStatement.setString(8, Venue.getCountry());
            preparedStatement.setDouble(9, Venue.getParkingFee());
            preparedStatement.executeUpdate();
            System.out.println("The venue has been added!");

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public Optional<Venue> getById(int id)
    {
        String getSql = "SELECT * FROM venues v WHERE v.id = ?";
        Connection connection = DatabaseConfig.getDbConnection();
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(getSql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return toVenue(resultSet);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    public List<Venue> show()
    {
        String showSql = "SELECT * FROM venues";
        Connection connection = DatabaseConfig.getDbConnection();
        List<Venue> Venues = new ArrayList<>();
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(showSql);
            // ResultSet starts out pointing before the first record
            Optional<Venue> Venue = toVenue(resultSet);
            while(Venue.isPresent())
            {
                Venues.add(Venue.get());
                Venue = toVenue(resultSet);
            }

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return Venues;
    }
    public void updateVenue(Venue newVenue, int id)
    {
        Connection connection = DatabaseConfig.getDbConnection();
        if(newVenue.getId() == id)
        {
            Optional<Venue> Venue = getById(id);
            if(Venue.isPresent())
            {
                String updateSql = "{call updateVenue(?,?,?,?,?,?,?,?,?)}";
                try {

                    CallableStatement callableStatement = connection.prepareCall(updateSql);
                    callableStatement.setInt(1,newVenue.getId());
                    callableStatement.setString(2, newVenue.getName());
                    callableStatement.setString(3,newVenue.getDescription());
                    callableStatement.setInt(4, newVenue.getCapacity());
                    callableStatement.setString(5,newVenue.getAddress());
                    callableStatement.setString(6, newVenue.getRegion());
                    callableStatement.setString(7, newVenue.getZIP());
                    callableStatement.setString(8, newVenue.getCountry());
                    callableStatement.setDouble(9, newVenue.getParkingFee());
                    callableStatement.execute();
                    System.out.println("The venues was updated successfully!");
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            else
                System.out.println("The Venue could not be found!");
        }
        else
            System.out.println("You can't update this Venue!");
    }
    public void delete(int id)
    {
        Optional<Venue> Venue = getById(id);
        if(Venue.isPresent())
        {
            try {
                Connection connection = DatabaseConfig.getDbConnection();
                String deleteSql = "DELETE FROM Venues WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                System.out.println("The venue was deleted succesfully!");

            }catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
            System.out.println("Nu exista niciun venue cu id-ul dat!");
    }
    private Optional<Venue> toVenue(ResultSet resultSet) throws  SQLException
    {
        if(resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String description = resultSet.getString(3);
            Integer capacity = resultSet.getInt(4);
            String address = resultSet.getString(5);
            String region = resultSet.getString(6);
            String zip = resultSet.getString(7);
            String country = resultSet.getString(8);
            Double parkingFee = resultSet.getDouble(9);
            return Optional.of(new Venue(id, name, description, capacity, address, region, zip, country,
                    parkingFee));
        }
        return Optional.empty();
    }

}
