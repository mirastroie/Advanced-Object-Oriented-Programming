import config.DatabaseConfig;
import config.TableBuilder;
import entities.*;
import services.DB.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public class DbMain {

    public static <T> void print(List<T> colectie) {
        if (colectie.size() == 0)
            System.out.println("\nColectia este goala!");
        else {
            System.out.println("\nColectia este: ");
            for (T t : colectie) {
                System.out.println(t);
            }
        }
    }

    public static void clientService() {
        ClientDbService clientDbService = ClientDbService.getUserDbService();
        List<Client> clientList = clientDbService.show();
        print(clientList);
        Client america = new Client(18, "0756123446", "america27", "america@gmail.com",
                "America Stevens", "mer12ABC");
        clientDbService.addClient(america);
        clientList = clientDbService.show();
        print(clientList);
        Client newAmerica = new Client(18, "0756123489", "america27", "america@gmail.com",
                "America Stevens", "mer12ABC");
        clientDbService.updateClient(newAmerica, 18);
        clientList = clientDbService.show();
        print(clientList);
        clientDbService.delete(18);
        clientList = clientDbService.show();
        print(clientList);

    }
    public static void employeeService(){
        EmployeeDbService employeeDbService = EmployeeDbService.getEmployeeDbService();
        Employee christine = new Employee(18,"She is known for her contralto vocals and her direct but " +
                "poignant lyrics, which focus on love and relationships. ", "Singer",
                "Christine-Anne", "Mcvie");
        print(employeeDbService.show());
        employeeDbService.addEmployee(christine);
        Employee newCrosby = new Employee(4, "He is a Canadian professional ice hockey player and captain of the Pittsburgh Penguins of the National Hockey League (NHL). Nicknamed Sid the Kid, Crosby was selected first overall by the Penguins in the 2005 NHL Entry Draft.", "Good Hockey player", "Sidney-Patrick", "Crosby");
        employeeDbService.updateEmployee(newCrosby,4);
        print(employeeDbService.show());
        employeeDbService.delete(18);
        print(employeeDbService.show());
    }
    public static void venueService() {
        VenueDbService venueDbService = VenueDbService.getVenueDbService();
        List<Venue> venueList = venueDbService.show();
        print(venueList);
        System.out.println("The venue having the id equal to 1: ");
        Optional<Venue> venue = venueDbService.getById(1);
        if (venue.isPresent())
            System.out.println(venueDbService.getById(1).get());
        else
            System.out.println("doesn't exist.");

        Venue thirdVenue = new Venue(34, "O2 Arena", "is a multi-purpose indoor arena in the centre " +
                "of The O2 entertainment complex on the Greenwich Peninsula in southeast London.", 20000,
                "Peninsula Square, London", "Greater London", "20110", "UK", 35);
        Venue fourthVenue = new Venue(34, "O2 Arena", "is a multi-purpose indoor arena in the centre " +
                "of The O2 entertainment complex on the Greenwich Peninsula in southeast London.", 25000,
                "Peninsula Square, London", "Greater London", "20110", "UK", 35);
        venueDbService.addVenue(thirdVenue);
        venueList = venueDbService.show();
        print(venueList);
        venueDbService.updateVenue(fourthVenue, 34);
        venueList = venueDbService.show();
        print(venueList);
        venueDbService.delete(19);
        venueDbService.delete(34);
        venueList = venueDbService.show();
        print(venueList);

    }

    public static void organizerService() {
        OrganizerDbService organizerDbService = OrganizerDbService.getOrganizerDbService();
        Organizer alexa = new Organizer(1, "New York's best organizer", "Alexa Rose",
                "alexarose", "123alexaA", "alexarose@gmail.com");
        organizerDbService.addOrganizer(alexa);
        List<Organizer> organizerList = organizerDbService.show();
        print(organizerList);
        organizerDbService.updateDescription("New York City's best organizer", 1);
        organizerList = organizerDbService.show();
        print(organizerList);
        Organizer newSydney = new Organizer(6, "Sydney Theatre Company has been a major force in Australian drama since its establishment in 1976.",
                "Sydney Theatre Company", "sydneytheatreCompany", "loveTheatre123", "planning@sydneytheatre.au");
        organizerDbService.updateOrganizer(newSydney,6);
        organizerList = organizerDbService.show();
        print(organizerList);
        organizerDbService.delete(1);
        organizerList = organizerDbService.show();
        print(organizerList);
    }

    public static void ticketService() {
        TicketDbService ticketDbService = TicketDbService.getTicketDbService();
        EventDbService eventDbService = EventDbService.getEventDbService();
        List<Ticket> ticketList = ticketDbService.show();
        print(ticketList);
        Ticket firstTicket = new Regular(2,49, 'E', 29, RefundPolicy.ChangeSeat,
                eventDbService.getById(1).get());
        Ticket first = new Premium(1, 3, 'K', 55, RefundPolicy.NoRefund, eventDbService.getById(2).get());
        ticketDbService.addTicket(first);
        ticketDbService.addTicket(firstTicket);
        print(ticketDbService.show());
        Ticket second = new Premium(1, 30, 'S', 89, RefundPolicy.ThirtyDaysCancellation, eventDbService.getById(2).get());
        ticketDbService.updateTicket(second, 1);
        ticketDbService.updateTicket(second, 2);
        print(ticketDbService.show());
        ticketDbService.delete(1);
        ticketDbService.delete(2);
        print(ticketDbService.show());

    }

    public static void eventService() {
        EventDbService eventDbService = EventDbService.getEventDbService();
        List<Event> eventList = eventDbService.show();
        print(eventList);
        EmployeeDbService employeeDbService = EmployeeDbService.getEmployeeDbService();
        List<Employee> employees = List.of(employeeDbService.getById(1).get(), employeeDbService.getById(2).get());
        Organizer organizer = OrganizerDbService.getOrganizerDbService().getById(7).get();
        Event firstEvent = new Event(21, "Rumours", LocalDateTime.of(2021, 9, 30,
                18, 30), LocalDateTime.of(2021, 9, 30, 20, 23),
                "A Fleetwood Mac concert celebrating the album's 44 anniversary.", 2000,
                10, VenueDbService.getVenueDbService().getById(3).get(), employees, organizer);
        Event updatedEvent = new Event(21, "Rumours - The concert", LocalDateTime.of(2021, 9, 30,
                18, 30), LocalDateTime.of(2021, 9, 30, 23, 23),
                "A Fleetwood Mac concert celebrating the album's 44 anniversary.", 2000,
                10, VenueDbService.getVenueDbService().getById(3).get(), List.of(employeeDbService.getById(1).get()), organizer);
        eventDbService.addEvent(firstEvent);
        eventList = eventDbService.show();
        print(eventList);
        eventDbService.updateVenue(2, 21);
        eventList = eventDbService.show();
        print(eventList);
        eventDbService.updateEvent(updatedEvent,21);
        eventList = eventDbService.show();
        print(eventList);
        eventDbService.delete(21);
        eventList = eventDbService.show();
        print(eventList);

    }

    public static void main(String[] args) {

        try {
            // construim tabelul tickets
            TableBuilder.createTable();
            venueService();
            organizerService();
            eventService();
            clientService();
            employeeService();
            ticketService();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            DatabaseConfig.closedDbConnection();
        }
    }
}
