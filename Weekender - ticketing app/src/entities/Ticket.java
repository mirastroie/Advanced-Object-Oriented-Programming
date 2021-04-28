package entities;

import java.util.Objects;

public abstract class Ticket {

    private final int id;
    private static int count = 0;
    private static int max = 1;
    private int section;
    private char row;
    private int seatNo;
    private RefundPolicy refundPolicy;
    private Event event;
    protected TicketStatus status;

    {
        count ++;
    }
    public abstract double price();
    public Ticket()
    {
        this.id = max ++;
        this.status = TicketStatus.AVAILABLE;
    }
    public Ticket(int section, char row, int seatNo, RefundPolicy refundPolicy, Event event)
    {
        this.id = max ++;
        this.section = section;
        this.row = row;
        this.seatNo = seatNo;
        this.refundPolicy = refundPolicy;
        this.event = event;
        this.status = TicketStatus.AVAILABLE;
    }
    public Ticket(int id,int section, char row, int seatNo, RefundPolicy refundPolicy, Event event)
    {
        this.section = section;
        this.row = row;
        this.seatNo = seatNo;
        this.refundPolicy = refundPolicy;
        this.event = event;
        this.id = id;
        max = Math.max(max,id) + 1;
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
        return section == ticket.section && row == ticket.row
                && seatNo == ticket.seatNo && refundPolicy == ticket.refundPolicy
                && Objects.equals(event, ticket.event) && status == ticket.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(section,row,seatNo,refundPolicy,event,status);
    }
}
