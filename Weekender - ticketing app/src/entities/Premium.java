package entities;

public class Premium extends Ticket {

    private static final int commission = 20;
    public Premium(int id, int section, char row, int seatNo, RefundPolicy refundPolicy, Event event, TicketStatus status)
    {
        super(id,section,row,seatNo,refundPolicy, event, status);
    }
    public Premium(int id, int section, char row, int seatNo, RefundPolicy refundPolicy, Event event)
    {
        super(id,section,row,seatNo,refundPolicy, event);
    }
    public Premium(int section, char row, int seatNo, RefundPolicy refundPolicy, Event event)
    {
        super(section,row,seatNo,refundPolicy, event);
    }
    public double price()
    {
        return getEvent().getBasePrice()*(float)(100 + commission)/100 + getEvent().getVenue().getParkingFee();
    }
}
