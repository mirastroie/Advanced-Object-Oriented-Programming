package entities;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organizer extends User{


    protected String description;
    protected List<Event> events;

    public void register(){}
    public Organizer(String description, String fullname, String username, String password, String email) {
        super(username,fullname,email,password);
        this.description = description;
        events = new ArrayList<Event>();
    }
    public Organizer(Integer id, String description, String fullname, String username, String password, String email) {
        super(id,username,fullname,email,password);
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
            Organizer organizer = (Organizer) obj;
            return Objects.equals(description, organizer.description) && Objects.equals(events, organizer.events);
        }
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 89 * hash + description.hashCode();
        hash = 89 * hash + (this.events != null ? events.hashCode() : 0);
        return hash;
    }
}
