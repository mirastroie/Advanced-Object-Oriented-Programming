package entities;

public class Premium extends Ticket {

    static final int commission = 20;
    public Premium(int section, char row, int seatNo, RefundPolicy refundPolicy, Event event)
    {
        super(section,row,seatNo,refundPolicy, event);
    }
    public double price()
    {
        return getEvent().getBasePrice()*(float)(100 + commission)/100 + getEvent().getVenue().getParkingFee();
    }
}
