package entities;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private static int nrOrders;
    private LocalDateTime datePurchased;
    private List<Ticket> tickets;
    protected double totalPrice;
    private Client client;

    public void computePrice(){
        double totalPrice = 0;
        for (Ticket ticket : tickets) totalPrice += ticket.price();
        this.totalPrice = totalPrice;
    }

    public Order(LocalDateTime datePurchased, List<Ticket> tickets)
    {
        nrOrders ++;
        this.id = nrOrders;
        this.datePurchased = datePurchased;
        this.tickets = tickets;

    }
    public void setClient(Client client)
    {
        this.client = client;
    }
    public Client getClient(){
        return client;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getNrOrders() {
        return nrOrders;
    }

    public static void setNrOrders(int nr) {
        nrOrders = nr;
    }

    public LocalDateTime getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(LocalDateTime datePurchased) {
        this.datePurchased = datePurchased;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder ticketsShow = new StringBuilder();
        for(int i = 0; i < tickets.size(); i ++ )
            ticketsShow.append("" + (i + 1) + ". " + tickets.get(i).toString() + "\n");

        return "Comanda " + id + " - " + datePurchased.format(formatter) + "\nPrice: " + totalPrice + "$. " +
                "\n Tickets: \n" + ticketsShow.toString();
    }
}
