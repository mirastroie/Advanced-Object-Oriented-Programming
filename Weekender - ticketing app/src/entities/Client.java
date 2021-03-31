package entities;
import java.util.ArrayList;
import java.util.List;

public class Client extends User {
    private String phoneNumber;
    private List<Order> orders;


    public Client(String phoneNumber, String username, String email, String fullname, String password)
    {
        super(username,fullname,email, password);
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

    @Override
    public String toString() {
        return super.toString() +
                "phoneNumber= " + phoneNumber;
    }
}
