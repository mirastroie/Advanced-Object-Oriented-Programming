package entities;
import java.util.ArrayList;
import java.util.List;
public class Organizer extends User{


    protected String description;
    protected List<Event> events;

    public void register(){}
    public Organizer(String description, String fullname, String username, String password, String email) {
        super(username,fullname,email,password);
        this.description = description;
        events = new ArrayList<Event>();
    }
    public Organizer(String fullname, String username, String password, String email) {
        super(username,fullname,email,password);
        events = new ArrayList<Event>();
    }

    public List<Event> getEvents()
    {
        return events;
    }
    public void setEvents(List<Event> events)
    {
        this.events = events;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + description + ") ";
    }
    public String show()
    {
        return fullname;
    }
}
