package entities;

import java.util.Objects;

public abstract class Ticket {

    private final int id;
    private static int count = 0;
    private int section;
    private char row;
    private int seatNo;
    private RefundPolicy refundPolicy;
    private Event event;
    protected TicketStatus status;

    public abstract double price();
    {
        count ++;
    }
    public Ticket()
    {
        this.id = count;
        this.status = TicketStatus.AVAILABLE;
    }
    public Ticket(int section, char row, int seatNo, RefundPolicy refundPolicy, Event event)
    {
        this.section = section;
        this.row = row;
        this.seatNo = seatNo;
        this.refundPolicy = refundPolicy;
        this.event = event;
        this.id = count;
        this.status = TicketStatus.AVAILABLE;
    }
    public void setStatus(TicketStatus status)
    {
        this.status = status;
    }
    public TicketStatus getStatus()
    {
        return status;
    }
    public Event getEvent(){
        return event;
    }
    public void setEvent(Event event){
        this.event = event;
    }
    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Ticket.count = count;
    }
    public int getId()
    {
        return id;
    }
    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public char getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public RefundPolicy getRefundPolicy() {
        return refundPolicy;
    }

    public void setRefundPolicy(RefundPolicy refundPolicy) {
        this.refundPolicy = refundPolicy;
    }

    @Override
    public String toString() {
        return "Ticket " + " for " + event.getName() + " event " + " - " + "Seat " + section + " " + row + " " + seatNo + "\nRefundPolicy: " + refundPolicy +
                "\nStatus: " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
