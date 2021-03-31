package services;

import entities.*;
import util.MyException;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;


public class OrderService {

    private List<Order> orders;
    private static OrderService orderService;
    private OrderService(){
        orders = new ArrayList<Order>();
    }
    public static OrderService getOrderService()
    {
        if(orderService == null)
            orderService = new OrderService();
        return orderService;
    }
    public void showOrders()
    {
        System.out.println();
        System.out.println("THE ORDERS ARE: ");
        for (Order order : orders)
            System.out.println(order.toString());
        System.out.println();
    }
    public void addOrder(User user, Order order) throws MyException
    {

        if(user instanceof Client) {
            Client client = (Client) user;

            order.computePrice();
            order.setClient(client);
            orders.add(order);

            List<Order> clientOrders = client.getOrders();
            clientOrders.add(order);
            client.setOrders(clientOrders);
        }
        else
            throw new MyException("Permission denied.");
    }

    public Order getOrder(int id)
    {
        for (Order order : orders)
            if (order.getId() == id)
                return order;
        return null;
    }
    public Ticket getTicket(int orderId, int ticketId) throws MyException
    {
        Order order = getOrder(orderId);
        if(order == null)
            throw new MyException("The order doesn't exist.");
        boolean found = false;
        for(Ticket ticket: order.getTickets())
            if(ticket.getId() == ticketId)
            {
                found = true;
                return ticket;
            }
        return null;
    }
    public void changeTicket(User user, int id, Ticket newTicket) throws MyException
    {

        try {

            if(user instanceof Client) {
                Client client = (Client) user;
                List<Order> clientOrders = client.getOrders();
                Order order = null;
                LocalDateTime now = LocalDateTime.now();
                //vream sa traversam orders-urile si sa vedem daca in vreuna din ele exista ticket-ul nostru
                for (Order clientOrder : clientOrders) {

                    Ticket oldTicket = getTicket(clientOrder.getId(), id);
                    if (oldTicket != null) {
                        order = clientOrder;
                        List<Ticket> tickets = order.getTickets();
                        if (oldTicket.getEvent().getStartTime().isAfter(now) && oldTicket.getStatus() != TicketStatus.CANCELLED &&
                                oldTicket.getRefundPolicy() == RefundPolicy.ChangeSeat &&
                                oldTicket.getEvent().equals(newTicket.getEvent()))
                        {
                            oldTicket.setSection(newTicket.getSection());
                            oldTicket.setRow(newTicket.getRow());
                            oldTicket.setSeatNo(newTicket.getSeatNo());
                            oldTicket.setRefundPolicy(newTicket.getRefundPolicy());
                            order.setTickets(tickets);

                        } else {
                            throw new MyException("The ticket can't be changed.");
                        }
                    }
                }
                if (order == null)
                    throw new MyException("The ticket doesn't exist.");
            }
            else
                throw new MyException("Permission denied.");
        }
        catch(MyException e)
        {
            throw e;
        }
    }
    public void deleteTicket(int ticketId, int orderId, User user) throws MyException
    {
        try {
            if(user instanceof Client) {
                Client client = (Client) user;
                Order existingOrder = getOrder(orderId);
                if (existingOrder == null)
                    throw new MyException("The order doesn't exist");
                if (!existingOrder.getClient().equals(client))
                    throw new MyException("Permission denied.");

                Ticket existingTicket = getTicket(orderId, ticketId);
                if (existingTicket == null)
                    throw new MyException("The ticket doesn't exist.");

                existingTicket.setStatus(TicketStatus.CANCELLED);
                if (existingTicket.getRefundPolicy() == RefundPolicy.ThirtyDaysCancellation) {
                    client.getCreditCard().setBalance(existingTicket.price());
                }
            }
            else
                throw new MyException("Permission denied.");
        }
        catch(MyException e)
        {
            throw e;
        }
    }

}
