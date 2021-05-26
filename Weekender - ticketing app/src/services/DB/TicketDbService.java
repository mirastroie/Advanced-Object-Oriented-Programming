package services.DB;

import config.DatabaseConfig;
import entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TicketDbService {
    private static TicketDbService service;
    private TicketDbService(){

    }
    public static TicketDbService getTicketDbService()
    {
        if(service == null)
            service = new TicketDbService();
        return service;
    }
    public void addTicket(Ticket Ticket)
    {
        String addTicket = "INSERT INTO tickets values(?,?,?,?,?,?,?,?)";
        Connection connection = DatabaseConfig.getDbConnection();
        try
        {
            // int id,int section, char row, int seatNo, RefundPolicy refundPolicy, Event event
            PreparedStatement preparedStatement = connection.prepareStatement(addTicket);
            preparedStatement.setInt(1,Ticket.getId());
            preparedStatement.setInt(2, Ticket.getSection());
            preparedStatement.setString(3,Character.toString(Ticket.getRow()));
            preparedStatement.setInt(4, Ticket.getSeatNo());
            preparedStatement.setString(5,Ticket.getRefundPolicy().toString());
            preparedStatement.setInt(6, Ticket.getEvent().getId());
            if(Ticket instanceof Premium)
                preparedStatement.setString(7, "P");
            else
                preparedStatement.setString(7, "R");
            preparedStatement.setString(8, Ticket.getStatus().toString());
            preparedStatement.executeUpdate();
            System.out.println("The ticket has been added!");

        }
        catch(SQLIntegrityConstraintViolationException e)
        {
            System.out.println("Two tickets can't have the same id!");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public Optional<Ticket> getById(int id)
    {
        String getSql = "SELECT * FROM Tickets v WHERE v.id = ?";
        Connection connection = DatabaseConfig.getDbConnection();
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(getSql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return toTicket(resultSet);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    public List<Ticket> show()
    {
        String showSql = "SELECT * FROM Tickets";
        Connection connection = DatabaseConfig.getDbConnection();
        List<Ticket> Tickets = new ArrayList<>();
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(showSql);
            Optional<Ticket> Ticket = toTicket(resultSet);
            while(Ticket.isPresent())
            {
                Tickets.add(Ticket.get());
                Ticket = toTicket(resultSet);
            }

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return Tickets;
    }
    public void updateTicket(Ticket newTicket, int id)
    {
        Connection connection = DatabaseConfig.getDbConnection();
        if(newTicket.getId() == id)
        {
            Optional<Ticket> Ticket = getById(id);
            if(Ticket.isPresent())
            {

                String updateSql = "UPDATE tickets SET section = ?," +
                        "row = ?, seatNo = ?, refundPolicy = ?, event_id = ?, status = ? " +
                        "WHERE id = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
                    // int id,int section, char row, int seatNo, RefundPolicy refundPolicy, Event event
                    preparedStatement.setInt(7,newTicket.getId());
                    preparedStatement.setInt(1, newTicket.getSection());
                    preparedStatement.setString(2,Character.toString(newTicket.getRow()));
                    preparedStatement.setInt(3, newTicket.getSeatNo());
                    preparedStatement.setString(4,newTicket.getRefundPolicy().toString());
                    preparedStatement.setInt(5, newTicket.getEvent().getId());
                    preparedStatement.setString(6, newTicket.getStatus().toString());
                    preparedStatement.executeUpdate();

                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            else
                System.out.println("The Ticket could not be found!");
        }
        else
            System.out.println("You can't update this Ticket!");
    }
    public void delete(int id)
    {
        Optional<Ticket> Ticket = getById(id);
        if(Ticket.isPresent())
        {
            try {
                Connection connection = DatabaseConfig.getDbConnection();
                String deleteSql = "DELETE FROM Tickets WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                System.out.println("The Ticket was deleted succesfully!");

            }catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
            System.out.println("Nu exista niciun Ticket cu id-ul dat!");
    }
    private Optional<Ticket> toTicket(ResultSet resultSet) throws  SQLException
    {
        if(resultSet.next()){
            // int id,int section, char row, int seatNo, RefundPolicy refundPolicy, Event event
            int id = resultSet.getInt(1);
            int section = resultSet.getInt(2);
            char row = resultSet.getString(3).charAt(0);
            int seatNo = resultSet.getInt(4);
            RefundPolicy refundPolicy = RefundPolicy.valueOf(resultSet.getString(5));
            int eventId = resultSet.getInt(6);
            String type = resultSet.getString(7);
            TicketStatus status = TicketStatus.valueOf(resultSet.getString(8));
            Event event = EventDbService.getEventDbService().getById(eventId).get();
            if(type.equals("P"))
                 return Optional.of(new Premium(id, section, row, seatNo, refundPolicy, event, status));
            else
                return Optional.of(new Regular(id, section, row, seatNo, refundPolicy, event, status));
        }
        return Optional.empty();
    }

}
