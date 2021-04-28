package entities;
import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class Event {
    private final Integer id;
    private String name;
    private static Integer max = 1;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    protected int availableSeats;
    protected double basePrice;
    protected Venue venue;
    protected List<Employee> lineup;
    protected Organizer organizer;


    public Event(String name, LocalDateTime startTime, LocalDateTime endTime, String description, int availableSeats,
                 double basePrice, Venue venue, List<Employee> lineup, Organizer organizer) {
        this.id =  max ++;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.availableSeats = availableSeats;
        this.basePrice = basePrice;
        this.venue = venue;
        this.lineup = lineup;
        this.organizer = organizer;
    }
    public Event(Integer id, String name, LocalDateTime startTime, LocalDateTime endTime, String description, int availableSeats,
                 double basePrice, Venue venue, List<Employee> lineup, Organizer organizer) {
        this.id = id;
        max = Math.max(max,id) + 1;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.availableSeats = availableSeats;
        this.basePrice = basePrice;
        this.venue = venue;
        this.lineup = lineup;
        this.organizer = organizer;
    }
    public Event(String name, LocalDateTime startTime, LocalDateTime endTime, String description, int availableSeats,
                 double basePrice, Venue venue, List<Employee> lineup) {

        this.id =  max ++;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.availableSeats = availableSeats;
        this.basePrice = basePrice;
        this.venue = venue;
        this.lineup = lineup;
    }

    public Event(String name, LocalDateTime startTime, LocalDateTime endTime, String description, int availableSeats,
                 double basePrice, Venue venue) {
        this.id = max ++ ;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.availableSeats = availableSeats;
        this.basePrice = basePrice;
        this.lineup = new ArrayList<Employee>();
        this.venue = venue;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public List<Employee> getLineup() {
        return lineup;
    }

    public void setLineup(List<Employee> lineup) {
        this.lineup = lineup;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return availableSeats == event.availableSeats && Double.compare(event.basePrice, basePrice) == 0 &&
                Objects.equals(name, event.name) && Objects.equals(startTime, event.startTime) &&
                Objects.equals(endTime, event.endTime) && Objects.equals(description, event.description) &&
                Objects.equals(venue, event.venue) && Objects.equals(lineup, event.lineup) &&
                Objects.equals(organizer, event.organizer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, description, availableSeats, basePrice, venue, lineup, organizer);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return name + " (Event) " + " - " + venue.show() + startTime.format(formatter) + " - " + endTime.format(formatter) +
                "\n" + "Description: " + description + "\n" + "Lineup: " +
                (lineup.size() < 1 ? "No listed acts yet." : Arrays.toString(lineup.toArray())) +
                "\nOrganizer: " + organizer.getFullname();
    }

}
