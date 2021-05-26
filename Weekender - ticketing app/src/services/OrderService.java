package services;

import entities.*;
import services.IO.Audit;
import services.IO.OrderIOService;
import services.IO.TicketIOService;
import util.MyException;
import util.NotFound;
import util.PermissionDenied;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderService {

    private List<Order> orders;
    private static OrderService orderService;
    private OrderService(){
        orders = OrderIOService.getOrderIOService().load();
        for(Order o : orders)
        {
            Client client = o.getClient();
            List<Order> clientOrders = client.getOrders();
            clientOrders.add(o);
            client.setOrders(clientOrders);
        }
    }
    public void close()
    {
        TicketIOService.getTicketIOService().save(orders.stream()
                .flatMap(i -> i.getTickets().stream()).collect(Collectors.toList()));
        OrderIOService.getOrderIOService().save(orders);
    }
    public static OrderService getOrderService()
    {
        if(orderService == null)
            orderService = new OrderService();
        return orderService;
    }
    public void showOrders()
    {
        Audit.getAudit().addAction("showOrders");
        System.out.println();
        System.out.println("THE ORDERS ARE: ");
        for (Order order : orders)
            System.out.println(order.toString());
        System.out.println();
    }
    public void addOrder(User user, Order order) throws MyException
    {

        Audit.getAudit().addAction("addOrder");
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
            throw new PermissionDenied();
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
            throw new NotFound("order");
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
        Audit.getAudit().addAction("changeTicket");
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
                    throw new NotFound("ticket");
            }
            else
                throw new PermissionDenied();
        }
        catch(MyException e)
        {
            throw e;
        }
    }
    public void deleteTicket(int ticketId, int orderId, User user) throws MyException
    {
        Audit.getAudit().addAction("deleteTicket");
        try {
            if(user instanceof Client) {
                Client client = (Client) user;
                Order existingOrder = getOrder(orderId);
                if (existingOrder == null)
                    throw new NotFound("order");
                if (!existingOrder.getClient().equals(client))
                    throw new PermissionDenied();

                Ticket existingTicket = getTicket(orderId, ticketId);
                UserService userService = UserService.getUserService();
                if (existingTicket == null)
                    throw new NotFound("ticket");

                existingTicket.setStatus(TicketStatus.CANCELLED);
                if (existingTicket.getRefundPolicy() == RefundPolicy.ThirtyDaysCancellation) {
                    client.getCreditCard().setBalance(existingTicket.price());
                }
            }
            else
                throw new PermissionDenied();
        }
        catch(MyException e)
        {
            throw e;
        }
    }
    public Set<Order> getOrdersWithinPriceRange(Double low, Double high)
    {
        return orders.stream()
                .filter(i -> low <= i.getTotalPrice() &&  i.getTotalPrice() <= high)
                .collect(Collectors.toSet());
    }
    public HashMap<Double, Set<Order>> getOrdersbyPriceRange()
    {
        Audit.getAudit().addAction("getOrdersbyPriceRange");
        HashMap<Double, Set<Order>> map =  new HashMap<>();
        Double firstLevel = 100.0;
        Double secondLevel = 140.0;
        Double thirdLevel = 400.0;
        map.put(firstLevel, getOrdersWithinPriceRange(0.0,firstLevel));
        map.put(secondLevel,getOrdersWithinPriceRange(firstLevel + 1, secondLevel));
        map.put(thirdLevel,getOrdersWithinPriceRange(secondLevel + 1, thirdLevel));
        return map;
    }
}
