package entities;

public class Regular extends Ticket {

    public Regular(int section, char row, int seatNo, RefundPolicy refundPolicy, Event event)
    {
        super(section,row,seatNo,refundPolicy,event);
    }
    public double price()
    {
        return getEvent().getBasePrice();
    }
}
