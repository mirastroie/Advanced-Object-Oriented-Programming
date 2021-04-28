package entities;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client extends User {
    private String phoneNumber;
    private List<Order> orders;
    private CreditCard creditCard;
    public Client(String phoneNumber, String username, String email, String fullname, String password)
    {
        super(username,fullname,email, password);
        this.phoneNumber = phoneNumber;
        orders = new ArrayList<>();
    }
    public Client(Integer id, String phoneNumber, String username, String email, String fullname, String password)
    {
        super(id, username,fullname,email, password);
        this.phoneNumber = phoneNumber;
        orders = new ArrayList<>();
    }
    public Client(String username, String email, String fullname, String password)
    {
        super(username,fullname,email, password);
        orders = new ArrayList<>();
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders)
    {
        this.orders = orders;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public String toString() {
        return super.toString() +
                "phoneNumber= " + phoneNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) return false;
        else {
            Client client = (Client) obj;
            return Objects.equals(phoneNumber, client.phoneNumber) && Objects.equals(orders, client.orders) &&
                    Objects.equals(creditCard, client.creditCard);
        }
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 89 * hash + phoneNumber.hashCode();
        hash = 89 * hash + (this.creditCard != null ? creditCard.hashCode() : 0);
        hash = 89 * hash + (this.orders != null ? orders.hashCode() : 0);
        return hash;
    }
}
