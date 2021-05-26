package entities;

public class Regular extends Ticket {

    public Regular(int section, char row, int seatNo, RefundPolicy refundPolicy, Event event)
    {
        super(section,row,seatNo,refundPolicy,event);
    }
    public Regular(int id, int section, char row, int seatNo, RefundPolicy refundPolicy, Event event)
    {
        super(id,section,row,seatNo,refundPolicy,event);
    }
    public Regular(int id, int section, char row, int seatNo, RefundPolicy refundPolicy, Event event, TicketStatus status)
    {
        super(id,section,row,seatNo,refundPolicy,event,status);
    }
    public double price()
    {
        return getEvent().getBasePrice();
    }

}
