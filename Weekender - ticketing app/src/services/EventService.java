package services;
import entities.*;
import util.MyException;
import validators.EventValidator;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDateTime;

import java.util.List;


public class EventService {

    private List<Event> events;
    private static EventService eventService;
    private EventService(){
          events = new ArrayList<Event>();
    }
    public static EventService getEventService()
    {
        if(eventService == null)
            eventService = new EventService();
        return eventService;
    }
    public void addEvent(Event event) throws MyException
    {
        try {

            UserService userService = UserService.getUserService();
            if(userService.getCurrentUser() instanceof Organizer) {

                Organizer organizer = (Organizer) userService.getCurrentUser();
                EventValidator validator = new EventValidator();
                String errors = validator.validateEvent(event, events);
                if (errors.length() > 0)
                    throw new MyException(errors);
                if (events.contains(event))
                    throw new MyException("The event is already added!");

                event.setOrganizer(organizer);
                //daca agajatii nu sunt deja adaugati, ii adaugam
                EmployeeService employeeService = EmployeeService.getEmployeeService();
                for (Employee employee : event.getLineup())
                    employeeService.addEmployee(employee);

                events.add(event);
                List<Event> organizerEvents = organizer.getEvents();
                organizerEvents.add(event);
                organizer.setEvents(organizerEvents);
            }
            else
                throw new MyException("Permission denied.");

        }
        catch(MyException e)
        {
            System.out.println("The event can't be added : " + e);
        }
    }
    public void showEvents()
    {
        System.out.println("THE EVENTS ARE: ");
        for(int i = 0; i < events.size(); i ++)
            System.out.println((i+1) + ". " + events.get(i).toString());
        System.out.println();
    }

    public List<Event> searchEvents(Venue venue)
    {
        LocalDateTime now = LocalDateTime.now();
        List<Event> foundEvents = new ArrayList<Event>();
        for (Event event : events)
            if (event.getVenue().equals(venue) && event.getStartTime().isAfter(now))
                foundEvents.add(event);

        System.out.println("----------------------");
        System.out.println("The events being held at " + venue.getName() + " are: ");
        for (Event foundEvent : foundEvents) System.out.println(foundEvent.toString());
        System.out.println("----------------------");
        return foundEvents;
    }
    public List<Event> searchEvents(LocalDateTime date)
    {

        List<Event> foundEvents = new ArrayList<Event>();
        for (Event event : events)
            if (event.getStartTime().isAfter(date))
                foundEvents.add(event);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("----------------------");
        System.out.println("The events after " + date.format(formatter) + " are: ");
        for (Event foundEvent : foundEvents)
            System.out.println(foundEvent.toString());
        System.out.println("----------------------");
        return foundEvents;
    }

    public List<Event> searchEvents(Employee employee)
    {
        LocalDateTime now = LocalDateTime.now();
        List<Event> foundEvents = new ArrayList<Event>();
        for (Event event : events) {
            List<Employee> employees = event.getLineup();
            if (employees.contains(employee) && event.getStartTime().isAfter(now))
                foundEvents.add(event);
        }
        System.out.println("----------------------");
        System.out.println("The events in which " + employee.getFirstName() + " " + employee.getLastName() + " is going to perform are: ");
        for (Event foundEvent : foundEvents)
            System.out.println(foundEvent.toString());
        System.out.println("----------------------");
        return foundEvents;
    }
}