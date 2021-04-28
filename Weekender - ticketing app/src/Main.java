import entities.*;
import services.*;
import util.MyException;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    static VenuesService venueService;
    static EmployeeService employeeService;
    static UserService userService;
    static EventService eventService;
    static OrderService orderService;

    public static void startServices()
    {
        // 0. "Pornirea" servicilor
        venueService = VenuesService.getVenuesService();
        employeeService = EmployeeService.getEmployeeService();
        userService = UserService.getUserService();
        eventService = EventService.getEventService();
        orderService = OrderService.getOrderService();
    }
    public static void venueServicesDemo()
    {
        // 1. Venue Service
        venueService.showVenues();
        venueService.removeVenue(3);
        venueService.showVenues();
        Venue firstVenue = new Venue( "Paris Arena", "The Paris Arena was " +
                "built for the 2024 Olympics that are set to be held in Paris, France.", -19, "address",
                "Paris", "9012", "france4", 90);
        Venue secondVenue = new Venue( "Hungarian State Opera House", "Designed by Miklós Ybl, the " +
                "Neo-Renaissance Hungarian State Opera House opened in 1884.", 2000, "Andrássy út 22",
                "Budapest", "10610", "Hungary", 20.7);
        Venue thirdVenue = new Venue( "O2 Arena", "is a multi-purpose indoor arena in the centre " +
                "of The O2 entertainment complex on the Greenwich Peninsula in southeast London.", 20000,
                "Peninsula Square, London", "Greater London", "20110", "UK", 35);
        venueService.addVenue(firstVenue);
        venueService.addVenue(secondVenue);
        venueService.addVenue(thirdVenue);
        venueService.showVenues();
    }
    public static void organizerEventServicesDemo() {
        // 2. User Service(Organizer), Employee Service, Event Service
        try {
            Organizer organizer = new Organizer("New York's best organizer", "Alexa Rose",
                    "alexarose", "123alexaA", "alexarose@gmail.com");
            userService.register(organizer);

            List<Employee> employees = new ArrayList<>();
            Employee christine = new Employee("She is known for her contralto vocals and her direct but " +
                    "poignant lyrics, which focus on love and relationships. ", "Singer",
                    "Christine-Anne", "Mcvie");
            Employee mick = new Employee("He is a British musician and actor, best known as the drummer," +
                    " co-founder, and leader of the rock band Fleetwood Mac.", "Singer", "Mick",
                    "Fleetwood");
            employees.add(christine);
            employees.add(mick);
            Event firstEvent = new Event("New beginnings", LocalDateTime.of(2021, 3, 31,
                    18, 30), LocalDateTime.of(2021, 4, 1, 12, 23),
                    "Spring event", 2230000,
                    10, venueService.getVenueById(1));
            Event secondEvent = new Event("Rumours", LocalDateTime.of(2021, 9, 30,
                    18, 30), LocalDateTime.of(2021, 9, 30, 20, 23),
                    "A Fleetwood Mac concert celebrating the album's 44 anniversary.", 2000,
                    10, venueService.getVenueById(1), employees);
            Event thirdEvent = new Event("Rolling Stones Story", LocalDateTime.of(2021, 9, 30,
                    14, 30), LocalDateTime.of(2021, 9, 30, 22, 23),
                    "A Rolling Stones concert.", 1500,
                    10, venueService.getVenueById(1));

            // Exemplu de date de autentificare:
            // username = alexarose
            // password = 123alexaA
            try {
                userService.signIn();
                // evenimentul nu o sa fie adaugat (atributul capacity depaseste capacitatea locatiei)
                eventService.addEvent(firstEvent);
                eventService.addEvent(secondEvent);
                eventService.addEvent(thirdEvent);
                eventService.showEvents();
                eventService.searchEvents(christine);
                eventService.searchEvents(venueService.getVenueById(1));
                eventService.searchEvents(LocalDateTime.of(2021, 6, 30, 12, 0));
                employeeService.showEmployees();
                if(userService.getCurrentUser() instanceof Organizer)
                {
                    Organizer currentOrganizer = (Organizer) userService.getCurrentUser();
                    System.out.println(currentOrganizer.getEvents());
                }


            } finally {

                userService.logOff();
            }
        } catch (MyException e) {
            System.out.println(e.toString());

        }
    }
    public static void clientOrderServicesDemo()
    {
        try {
            // 3. User Service(Client), Order service
            Client america = new Client("0756123446", "america27", "america@gmail.com",
                    "America Stevens", "mer12ABC");
            userService.register(america);

            try {
                // Exemplu de date de autentificare:
                // username  = andrew.flores
                // password = Ferr2is
                userService.signIn();
                userService.changeCreditCard(userService.getCurrentUser(), new CreditCard(1, CardType.VISA,
                        "373826021134959", "01", "2029", "Luana Stevens"));
                userService.showUsers();

                List<Ticket> firstOrderTickets = new ArrayList<>();
                Ticket firstTicket = new Regular(49, 'E', 29, RefundPolicy.ChangeSeat,
                        eventService.getEventById(1));
                firstOrderTickets.add(firstTicket);
                Order firstOrder = new Order(LocalDateTime.now(), firstOrderTickets);
                orderService.addOrder(userService.getCurrentUser(), firstOrder);
                System.out.println("Id-ul user-ului cureent este " + userService.getCurrentUser().getId());
                List<Ticket> secondOrderTickets = new ArrayList<>();
                secondOrderTickets.add(new Premium(3, 'K', 55, RefundPolicy.NoRefund,
                        eventService.getEventById(2)));
                secondOrderTickets.add(new Premium(30, 'S', 89, RefundPolicy.ThirtyDaysCancellation,
                        eventService.getEventById(4)));
                secondOrderTickets.add(new Premium(45, 'E', 22, RefundPolicy.ThirtyDaysCancellation,
                        eventService.getEventById(5)));

                Order secondOrder = new Order(LocalDateTime.now().minusDays(3), secondOrderTickets);
                orderService.addOrder(userService.getCurrentUser(), secondOrder);

                userService.viewHistory(userService.getCurrentUser());
                Order thirdOrder = orderService.getOrder(secondOrder.getId());
                orderService.deleteTicket(secondOrderTickets.get(1).getId(), thirdOrder.getId(),
                        userService.getCurrentUser());

                if(userService.getCurrentUser() instanceof Client)
                {
                    Client client = (Client)userService.getCurrentUser();
                    System.out.println("Your balance is: " + client.getCreditCard().getBalance());
                }
                Ticket newTicket = new Regular(34, 'F', 98, RefundPolicy.NoRefund,
                        eventService.getEventById(1));
                orderService.changeTicket(userService.getCurrentUser(), firstTicket.getId(), newTicket);
                userService.viewHistory(userService.getCurrentUser());
                userService.viewCalendar(userService.getCurrentUser());

            } catch (MyException e) {
                System.out.println(e.toString());
            } finally {
                userService.logOff();
            }
        }catch (MyException e)
        {
            System.out.println(e.toString());
        }
    }
    public static void clientPrivilegeDemo()
    {
        try {
            // Un organizator NU poate plasa comenzi:
            // Exemplu de date pentru autentificare:
            // username = kaptivesports
            // password = K3pt2ve
            userService.signIn();
            Ticket secondTicket = new Regular(59, 'G', 43, RefundPolicy.ThirtyDaysCancellation,
                    eventService.getEventById(1));
            Order order4 = new Order(LocalDateTime.now().minusDays(3), new ArrayList<>(Arrays.asList(secondTicket)));
            orderService.addOrder(userService.getCurrentUser(), order4);

        } catch (MyException e) {
            System.out.println(e.toString());
        } finally {
            userService.logOff();
        }
    }
    public static void orderServiceDemo()
    {

        orderService.showOrders();
        HashMap<Double, Set<Order>> map = orderService.getOrdersbyPriceRange();
        for (Map.Entry<Double, Set<Order>> doubleSetEntry : map.entrySet()) {
            System.out.println("The orders with the total prices lower than" + ((Map.Entry) doubleSetEntry).getKey() +
                    " are: \n" + ((Map.Entry) doubleSetEntry).getValue());
        }
    }
    public static void closeServices()
    {
        // 4. "Oprirea" servicilor
        employeeService.close();
        venueService.close();
        eventService.close();
        userService.close();
        orderService.close();
    }
    public static void main(String[] args) {


        try {
            startServices();
            venueServicesDemo();
            organizerEventServicesDemo();
            clientOrderServicesDemo();
            clientPrivilegeDemo();
            orderServiceDemo();

        }catch(Exception e)
        {
            System.out.println(e.toString());

        }finally {
            closeServices();
        }
    }
}
