package services.IO;
import entities.Employee;
import entities.Event;
import entities.Organizer;
import services.EmployeeService;
import services.UserService;
import services.VenuesService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventIOService extends AbstractIOService<Event> {
    private static EventIOService service;
    public static EventIOService getEventIOService()
    {
        if(service == null) {

            service = new EventIOService();
        }
        return service;
    }
    public List<Event> load()
    {
        String FILE_NAME = "data/events.csv";
        return super.load(FILE_NAME);
    }
    public void save(List<Event> s)
    {
        String FILE_NAME = "data/events.csv";
        String HEADER_FILE = "Id, Name, Start Date, End Date, Description, Available Seats," +
                " Base Price, Venue, Employees, Organizer";
        super.save(s,FILE_NAME, HEADER_FILE);
    }
    public Event parse(List<String> entry)
    {
        try {
            EmployeeService empService = EmployeeService.getEmployeeService();
            UserService userService = UserService.getUserService();
            VenuesService venuesService = VenuesService.getVenuesService();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
            List<String> stringIDs = List.of(entry.get(8).trim().replace("[","")
                    .replace("]","").split(","));
            List<Employee> employees = new ArrayList<>();
            if (stringIDs.size() > 0 && !(stringIDs.size() == 1 && stringIDs.get(0).equals(""))) {
                List<Integer> IDs = stringIDs.stream().map(String::trim)
                        .map(Integer::parseInt).collect(Collectors.toList());
                employees = empService.getEmployeesByIds(IDs);
            }
            Organizer organizer = userService.getOrganizerById(Integer.parseInt(entry.get(9)));

            return new Event(Integer.parseInt(entry.get(0)), entry.get(1),
                    LocalDateTime.parse(entry.get(2), formatter),
                    LocalDateTime.parse(entry.get(3), formatter),
                    entry.get(4),
                    Integer.parseInt(entry.get(5)),
                    Double.parseDouble(entry.get(6)),
                    venuesService.getVenueById(Integer.parseInt(entry.get(7))),
                    employees,
                    organizer);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            return null;
        }
    }
    public String unparse(Event event)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
        String empIDs = "[]";
        if(event.getLineup().size() != 0 && event.getLineup().stream().anyMatch(Objects::nonNull)) {
            Optional<String> employeesIds = event.getLineup()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(i -> String.valueOf(i.getId()))
                    .reduce((a, b) -> a + ", " + b);
            if(employeesIds.isPresent())
                empIDs = employeesIds.get();
        }
        return event.getId() + ", " + event.getName() + ", " + event.getStartTime().format(formatter) + ", " +
                event.getEndTime().format(formatter) + ", \"" + event.getDescription() + "\", " +
                event.getAvailableSeats() + ", " + event.getBasePrice() + ", " + event.getVenue().getId() +
                ", \"" + empIDs + "\", " + event.getOrganizer().getId();
    }

}
