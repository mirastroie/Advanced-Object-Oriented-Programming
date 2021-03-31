import entities.*;
import services.*;
import util.MyException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {



        EmployeeService employeeService = EmployeeService.getEmployeeService();
        VenuesService venueService = VenuesService.getVenuesService();
        EventService eventService = EventService.getEventService();
        UserService userService = UserService.getUserService();
        OrderService orderService = OrderService.getOrderService();

        //testam functionalitatea serviciului VenueService
        Venue venue = new Venue("Madison Square Garden", "MSG is a multi-purpose indoor arena in New York City," +
                " being the oldest major sporting facility in the NY metropolitan area.",
                20000, "4 Pennsylvania Plaza, NYC","New York","10010","US",50.6);
        venueService.addVenue(venue);
        Venue venue2 = new Venue("Philippine Arena", "The world's largest indoor arena. Arranged on a greenfield site " +
                "north of Manila, at Ciudad de Victoria in Sta. Maria Bulacan, the arena has been intended to have the greatest " +
                "conceivable number of individuals. ",
                55000, "Ciudad de Victoria, Bocaue","Bulacan","05976","Philippines",100.2);
        venueService.addVenue(venue2);
        Venue venue3 = new Venue("Hungarian State Opera House","Designed by Miklós Ybl, the Neo-Renaissance Hungarian " +
                "State Opera House opened in 1884.",2000,"Andrássy út 22","Budapest","10610","Hungary",
                20.7);
        venueService.addVenue(venue3);
        venueService.showVenues();
        venueService.removeVenue(venue2.getId());
        Venue venue4 = new Venue("Paris La Défense Arena","The Paris La Defense Arena was built for the 2024 Olympics that" +
                " are set to be held in Paris, France.",-19,"address","Paris","9012","france4",90);
        venueService.addVenue(venue4);
        venueService.showVenues();
        venueService.addVenue(venue2);


        //testam functionalitatea serviciului EventService si EmployeeService
        try {

            Organizer organizer = new Organizer("New York best organizer", "Alexa Rose", "alexarose",
                    "123alexaA", "alexarose@gmail.com");
            userService.register(organizer);

            List<Employee> employees = new ArrayList<Employee>();
            Employee stevie = new Employee(" She is known for her distinctive voice, mystical stage persona and poetic," +
                    " symbolic lyrics", "Singer", "Stevie-Lynn", "Nicks");
            Employee mick = new Employee("He is a British musician and actor, best known as the drummer, co-founder, and " +
                    "leader of the rock band Fleetwood Mac.","Singer","Mick","Fleetwood");
            employees.add(stevie);
            employees.add(mick);
            Event event = new Event("July Vibes", LocalDateTime.of(2021, 7, 21, 18, 0),
                    LocalDateTime.of(2021, 7, 29, 12, 0), "Fleetwood Mac concert",
                    20000, 150, venue, employees, organizer);
            Event event2 = new Event("Universal", LocalDateTime.of(2021, 9, 10, 12, 23),
                    LocalDateTime.of(2021, 9, 11, 3, 0), "Movie marathon", 100,
                    20, venue2);
            Event event3 = new Event("New beginnings", LocalDateTime.of(2021, 3, 31, 18, 30),
                    LocalDateTime.of(2021, 4, 1, 12, 23), "Spring event", 2230000,
                    10, venue2);
            Event event4 = new Event("New York Rangers vs Pittsburgh Penguins",LocalDateTime.of(2021,4,6,
                    19,0),LocalDateTime.of(2021,4,6,21,30),"The New York Rangers" +
                    " play the Pittsburgh Penguins at Madison Square Garden on April 6, 2021.",10000,70,venue);
            Event event5 = new Event("New beginnings", LocalDateTime.of(2021, 3, 30, 18, 30),
                    LocalDateTime.of(2021, 3, 30, 20, 23), "Spring event", 2000,
                    10, venue2);

            // ne inregistram drept organizator cu username = alexarose si parola = 123alexaA
            try {
                userService.signIn();
                eventService.addEvent(event);
                eventService.addEvent(event2);
                eventService.addEvent(event3);
                // evenimentul event4 nu o sa fie adaugat din cauza ca atributul capacity depaseste capacitatea locatiei
                eventService.addEvent(event4);
                eventService.addEvent(event5);
            }
            catch(MyException e)
            {
                throw e;
            }
            finally {

                userService.logOff();
            }
            eventService.showEvents();
            eventService.searchEvents(stevie);
            eventService.searchEvents(venue);
            eventService.searchEvents(LocalDateTime.of(2021,6,30,12,0));
            employeeService.showEmployees();

            //ilustrarea functionalitatiilor oferite de UserService si OrderService
            Client america = new Client("0756123446","america27","america@gmail.com","America Stevens",
                    "mer12ABC");
            userService.register(america);

            try {
                //ne inregistram drept client cu username  = america27 si password = mer12ABC
                userService.signIn();
                userService.editCredentials(userService.getCurrentUser(), new CreditCard(CardType.VISA, "373826021134959", "01",
                        "2029", "Luana Stevens"));
                userService.showUsers();

                Ticket ticket1 = new Regular(45, 'E', 20, RefundPolicy.ChangeSeat, event);
                List<Ticket> lista = new ArrayList<>();
                lista.add(ticket1);
                Order order = new Order(LocalDateTime.now(), lista);
                orderService.addOrder(userService.getCurrentUser(), order);

                List<Ticket> tickets2 = new ArrayList<>();
                tickets2.add(new Premium(3, 'F', 55, RefundPolicy.NoRefund, event2));
                tickets2.add(new Premium(30, 'F', 89, RefundPolicy.ThirtyDaysCancellation, event4));
                tickets2.add(new Premium(45, 'A', 22, RefundPolicy.ThirtyDaysCancellation, event5));
                Order order2 = new Order(LocalDateTime.now().minusDays(3), tickets2);
                orderService.addOrder(userService.getCurrentUser(), order2);
                userService.viewHistory(userService.getCurrentUser());
                Order order3 = orderService.getOrder(order2.getId());
                orderService.deleteTicket(tickets2.get(1).getId(), order3.getId(), userService.getCurrentUser());
                Ticket newTicket = new Regular(34, 'F', 98, RefundPolicy.NoRefund, event);
                orderService.changeTicket(userService.getCurrentUser(), ticket1.getId(), newTicket);
                userService.viewHistory(userService.getCurrentUser());
                userService.viewCalendar(userService.getCurrentUser());


            }catch(MyException e)
            {
                System.out.println(e.toString());
            }
            finally {
                userService.logOff();
            }
        try
        {
            // Un organizator NU poate plasa comenzi:
            // ne inregistram cu username = alexarose si parola = 123alexaA
            userService.signIn();
            Ticket ticket3 = new Regular(58,'G',43,RefundPolicy.ThirtyDaysCancellation,event);
            Order order4 = new Order(LocalDateTime.now().minusDays(3), new ArrayList<Ticket>(Arrays.asList(ticket3)));
            orderService.addOrder(userService.getCurrentUser(),order4);

        }
        catch(MyException e)
        {
            System.out.println(e.toString());
        }
        finally {
            userService.logOff();
        }
        orderService.showOrders();
        }
        catch(MyException e)
        {
            System.out.println(e.toString());
        }
    }
}
