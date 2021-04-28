package services.IO;
import entities.Order;
import entities.Ticket;
import services.UserService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderIOService extends AbstractIOService<Order>{
    private static OrderIOService service;
    private static List<Ticket> tickets;
    public static OrderIOService getOrderIOService()
    {
        if(service == null) {
            tickets = TicketIOService.getTicketIOService().load();
            service = new OrderIOService();
        }
        return service;
    }
    public List<Order> load()
    {
        String FILE_NAME = "data/orders.csv";
        return super.load(FILE_NAME);
    }
    public void save(List<Order> s)
    {
        String FILE_NAME = "data/orders.csv";
        String HEADER_FILE = "Id, Date of Purchase, Tickets, Client";
        super.save(s,FILE_NAME, HEADER_FILE);
    }
    public Order parse(List<String> value)
    {
        UserService userService = UserService.getUserService();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
        List<String> stringIDs = List.of(value.get(2).trim().replace("]","")
                .replace("[","").split(","));
        List<Integer> IDs = stringIDs.stream().map(Integer::parseInt).collect(Collectors.toList());

        List<Ticket> orderTickets = List.of();
        if (IDs.size() > 0 && !(IDs.size() == 1 && IDs.get(0) == null))
            orderTickets = tickets.stream()
                        .filter(elem -> IDs.contains(elem.getId())).collect(Collectors.toList());


        return new Order(Integer.parseInt(value.get(0)),LocalDateTime.parse(value.get(1),formatter),
                     orderTickets,
                     userService.getClientById(Integer.parseInt(value.get(3)))
                    );
    }
    public String unparse(Order order) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
        Optional<String> ticketsIds = order.getTickets()
                .stream()
                .map(elem -> String.valueOf(elem.getId()))
                .reduce((a, b) -> a + "," + b);
        return order.getId() + ", " + order.getDatePurchased().format(formatter) + ", \"[" + ticketsIds.get() +
                "]\", " + order.getClient().getId();
    }
}
