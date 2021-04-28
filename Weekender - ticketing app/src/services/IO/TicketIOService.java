package services.IO;
import entities.*;
import services.EventService;
import java.util.List;

public class TicketIOService extends AbstractIOService<Ticket>{
    private static TicketIOService service;
    public static TicketIOService getTicketIOService()
    {
        if(service == null) {

            service = new TicketIOService();
        }
        return service;
    }
    public List<Ticket> load()
    {
        String FILE_NAME = "data/tickets.csv";
        return super.load(FILE_NAME);
    }
    public void save(List<Ticket> s)
    {
        String FILE_NAME = "data/tickets.csv";
        String HEADER_FILE = "Id, Section, Row, Seat Number, Refund Policy, Event, Type";
        super.save(s,FILE_NAME, HEADER_FILE);
    }
    public Ticket parse(List<String> entry)
    {

        RefundPolicy refundPolicy = switch (entry.get(4).trim()) {
            case "ThirtyDaysCancellation" -> RefundPolicy.ThirtyDaysCancellation;
            case "ChangeSeat" -> RefundPolicy.ChangeSeat;
            default -> RefundPolicy.NoRefund;
        };


        Event event = EventService.getEventService().getEventById(Integer.parseInt(entry.get(5)));
        if(entry.get(6).trim().equals("Premium"))
           return new Premium(Integer.parseInt(entry.get(0)), Integer.parseInt(entry.get(1)), entry.get(2).charAt(0),
                   Integer.parseInt(entry.get(3)), refundPolicy, event);
        else
            return new Regular(Integer.parseInt(entry.get(0)), Integer.parseInt(entry.get(1)), entry.get(2).charAt(0),
                    Integer.parseInt(entry.get(3)), refundPolicy,event);
    }
    public String unparse(Ticket ticket)
    {
        return ticket.getId() + ", " + ticket.getSection() + ", " + ticket.getRow() + ", " + ticket.getSeatNo() + ", " +
                ticket.getRefundPolicy().name() + ", " + ticket.getEvent().getId() + ", "
                + (ticket instanceof Premium ? "Premium" : "Regular");
    }
}
