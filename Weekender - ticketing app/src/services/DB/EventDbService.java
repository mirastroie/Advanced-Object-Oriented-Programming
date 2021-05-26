package services.DB;

import config.DatabaseConfig;
import entities.Employee;
import entities.Event;
import entities.Organizer;
import entities.Venue;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventDbService {
    private static EventDbService service;
    private EventDbService(){

    }
    public static EventDbService getEventDbService()
    {
        if(service == null)
            service = new EventDbService();
        return service;
    }
    public void addEventEmployee(int eventId, int empId) throws SQLException {
        Connection connection = DatabaseConfig.getDbConnection();
        String sqlInsertEvEm = "INSERT INTO EVENTSEMPLOYEES VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertEvEm);
        preparedStatement.setInt(1,eventId);
        preparedStatement.setInt(2,empId);
        preparedStatement.executeUpdate();

    }
    public void addEvent(Event Event)
    {
        String addEvent = "INSERT INTO Events values(?,?,?,?,?,?,?,?,?)";
        Connection connection = DatabaseConfig.getDbConnection();
        try
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            PreparedStatement preparedStatement = connection.prepareStatement(addEvent);
            preparedStatement.setInt(1,Event.getId());
            preparedStatement.setString(2, Event.getName());
            preparedStatement.setString(3,Event.getStartTime().format(formatter));
            preparedStatement.setString(4, Event.getEndTime().format(formatter));
            preparedStatement.setString(5,Event.getDescription());
            preparedStatement.setInt(6, Event.getAvailableSeats());
            preparedStatement.setDouble(7, Event.getBasePrice());
            preparedStatement.setInt(8,Event.getVenue().getId());
            preparedStatement.setInt(9,Event.getOrganizer().getId());
            preparedStatement.executeUpdate();
            for(Employee emp : Event.getLineup())
            {
                addEventEmployee(Event.getId(), emp.getId());
            }
            System.out.println("The event has been added!");

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public Optional<Event> getById(int id)
    {
        String getByIdCmd = "SELECT * FROM Events c WHERE c.id = ?";
        Connection connection = DatabaseConfig.getDbConnection();
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(getByIdCmd);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return toEvent(resultSet);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    public List<Event> show()
    {
        String showSql = "SELECT * FROM Events";
        Connection connection = DatabaseConfig.getDbConnection();
        List<Event> Events = new ArrayList<>();
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(showSql);
            // ResultSet starts out pointing before the first record
            Optional<Event> Event = toEvent(resultSet);
            while(Event.isPresent())
            {
                Events.add(Event.get());
                Event = toEvent(resultSet);
            }

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return Events;
    }

    public void updateEvent(Event newEvent, int id)
    {
        Connection connection = DatabaseConfig.getDbConnection();
        if(newEvent.getId() == id)
        {
            Optional<Event> event = getById(id);
            if(event.isPresent())
            {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String updateSql = "UPDATE events SET name = ?," +
                        "startDate = ?, endDate = ?, description = ?, availableSeats = ?, basePrice = ?, venue = ?, organizer = ? " +
                        "WHERE id = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
                    preparedStatement.setInt(9,newEvent.getId());
                    preparedStatement.setString(1, newEvent.getName());
                    preparedStatement.setString(2,newEvent.getStartTime().format(formatter));
                    preparedStatement.setString(3, newEvent.getEndTime().format(formatter));
                    preparedStatement.setString(4,newEvent.getDescription());
                    preparedStatement.setInt(5, newEvent.getAvailableSeats());
                    preparedStatement.setDouble(6, newEvent.getBasePrice());
                    preparedStatement.setInt(7,newEvent.getVenue().getId());
                    preparedStatement.setInt(8,newEvent.getOrganizer().getId());
                    preparedStatement.executeUpdate();

                    // actualizam si employees
                    deleteEmployeeEventEntries(newEvent.getId(), connection);
                    for(Employee emp : newEvent.getLineup())
                    {
                        addEventEmployee(newEvent.getId(), emp.getId());
                    }


                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            else
                System.out.println("The event could not be found!");
        }
        else
            System.out.println("You can't update this event!");
    }

    public void updateVenue(int venueId, int id)
    {
        Connection connection = DatabaseConfig.getDbConnection();
        Optional<Event> Event = getById(id);
        if(Event.isPresent())
        {
            try {
                VenueDbService venueDbService = VenueDbService.getVenueDbService();
                CallableStatement callableStatement = connection.prepareCall("{call updateEventVenue(?,?,?)}");
                callableStatement.setInt(1,id);
                callableStatement.setInt(2, venueId);
                callableStatement.registerOutParameter(3, Types.INTEGER);
                callableStatement.execute();
                int old_id = callableStatement.getInt(3);
                System.out.println("The venue event was changed from " +  venueDbService.getById(old_id).get().getName() + " to " + venueDbService.getById(venueId).get().getName() + "!");
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
            System.out.println("The Event could not be found!");

    }
    public void deleteEmployeeEventEntries(int id, Connection connection) throws SQLException {
        String joinSql = "DELETE FROM EVENTSEMPLOYEES WHERE event_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(joinSql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
    public void delete(int id)
    {
        Optional<Event> Event = getById(id);
        if(Event.isPresent())
        {
            try {
                Connection connection = DatabaseConfig.getDbConnection();
                String deleteSql = "DELETE FROM Events WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                deleteEmployeeEventEntries(id, connection);

            }catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
            System.out.println("Nu exista niciun user cu id-ul dat!");
    }
    private Optional<Event> toEvent(ResultSet resultSet) throws  SQLException
    {
        if(resultSet.next()){

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            LocalDateTime startDate = LocalDateTime.parse(resultSet.getString(3),formatter);
            LocalDateTime endDate = LocalDateTime.parse(resultSet.getString(4),formatter);
            String description = resultSet.getString(5);
            int availableSeats = resultSet.getInt(6);
            double basePrice = resultSet.getDouble(7);
            // cu ajutorul serviciilor destinate locatiilor si organizatorilor
            // preluam entitatiile corespunzatoare id-urilor
            int venueId = resultSet.getInt(8);
            int organizerId = resultSet.getInt(9);
            Venue venue = VenueDbService.getVenueDbService().getById(venueId).get();
            Organizer organizer = OrganizerDbService.getOrganizerDbService().getById(organizerId).get();
            // trebuie sa preluam si angajatii
            List<Employee> employees = EmployeeDbService.getEmployeeDbService().showByEvent(id);
            return Optional.of(new Event(id, name, startDate, endDate, description, availableSeats, basePrice, venue, employees,organizer));
        }
        return Optional.empty();
    }

}
