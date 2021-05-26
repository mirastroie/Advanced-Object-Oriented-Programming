package services;
import entities.*;
import services.IO.Audit;
import services.IO.EventIOService;
import util.AlreadyAdded;
import util.MyException;
import util.PermissionDenied;
import validators.EventValidator;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class EventService {

    private List<Event> events;
    private static EventService eventService;
    private EventService(){
        events = EventIOService.getEventIOService().load();
        //System.out.println(events);
        for(Event event: events)
        {
            if(event.getOrganizer() != null)
            {
                Organizer organizer = event.getOrganizer();
                List<Event> organizerEvents = organizer.getEvents();
                organizerEvents.add(event);
                organizer.setEvents(organizerEvents);
            }
        }
    }
    public void close()
    {
        EventIOService.getEventIOService().save(events);
    }
    public static EventService getEventService()
    {
        if(eventService == null)
            eventService = new EventService();
        return eventService;
    }
    public boolean isCurrentVenue(int venueId)
    {
        Set<Integer> currentVenuesIds= events.stream().map(i -> i.getVenue().getId()).collect(Collectors.toSet());
        return currentVenuesIds.contains(venueId);
    }
    public Event getEventById(int id)
    {
        Optional<Event> event = events.stream().filter(elem -> elem.getId() == id).findFirst();
        return event.orElse(null);
    }
    public void addEvent(Event event) throws MyException
    {
        Audit.getAudit().addAction("addEvent");
        try {

            UserService userService = UserService.getUserService();
            if(userService.getCurrentUser() instanceof Organizer) {

                Organizer organizer = (Organizer) userService.getCurrentUser();
                EventValidator validator = new EventValidator();
                String errors = validator.validateEvent(event, events);
                if (errors.length() > 0)
                    throw new MyException(errors);
                if (events.contains(event))
                    throw new AlreadyAdded("event");

                event.setOrganizer(organizer);
                //daca agajatii nu sunt deja adaugati in EmployeeService, ii adaugam
                EmployeeService employeeService = EmployeeService.getEmployeeService();
                for (Employee employee : event.getLineup())
                    employeeService.addEmployee(employee);

                events.add(event);
                List<Event> organizerEvents = organizer.getEvents();
                organizerEvents.add(event);
                organizer.setEvents(organizerEvents);
            }
            else
                throw new PermissionDenied();

        }
        catch(MyException e)
        {
            System.out.println("The event can't be added : " + e);
        }
    }
    public void showEvents()
    {
        Audit.getAudit().addAction("showEvents");
        System.out.println("THE EVENTS ARE: ");
        for(int i = 0; i < events.size(); i ++)
            System.out.println((i+1) + ". " + events.get(i).toString());
        System.out.println();
    }

    public List<Event> searchEvents(Venue venue)
    {
        Audit.getAudit().addAction("searchEvents");
        VenuesService venuesService = VenuesService.getVenuesService();
        List<Event> foundEvents = new ArrayList<Event>();
        if(venuesService.getVenues().contains(venue)) {
            LocalDateTime now = LocalDateTime.now();
            for (Event event : events)
                if (event.getVenue().equals(venue) && event.getStartTime().isAfter(now))
                    foundEvents.add(event);

            System.out.println("----------------------");
            System.out.println("The events being held at " + venue.getName() + " are: ");
            foundEvents.forEach(System.out::println);
            System.out.println("----------------------");

            return foundEvents;
        }
        else
            System.out.println("The venue could not be found!");
        return foundEvents;
    }
    public List<Event> searchEvents(LocalDateTime date)
    {
        Audit.getAudit().addAction("searchEvents");
        List<Event> foundEvents = new ArrayList<Event>();
        for (Event event : events)
            if (event.getStartTime().isAfter(date))
                foundEvents.add(event);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
        Consumer<Event> show = System.out::println;
        System.out.println("----------------------");
        System.out.println("The events after " + date.format(formatter) + " are: ");
        for(Event event: foundEvents)
                  show.accept(event);
        System.out.println("----------------------");

        return foundEvents;
    }

    public List<Event> searchEvents(Employee employee)
    {
        Audit.getAudit().addAction("searchEvents");
        EmployeeService employeeService = EmployeeService.getEmployeeService();
        List<Event> foundEvents = new ArrayList<Event>();
        if(employeeService.getEmployeeById(employee.getId())!=null) {
            LocalDateTime now = LocalDateTime.now();

            for (Event event : events) {
                List<Employee> employees = event.getLineup();
                if (employees.contains(employee) && event.getStartTime().isAfter(now))
                    foundEvents.add(event);
            }
            System.out.println("----------------------");
            System.out.println("The events in which " + employee.getFirstName() + " " + employee.getLastName() +
                    " is going to perform are: ");
            for (Event foundEvent : foundEvents)
                System.out.println(foundEvent.toString());
            System.out.println("----------------------");
        }
        else
            System.out.println("The employee could not be found");
        return foundEvents;
    }
}