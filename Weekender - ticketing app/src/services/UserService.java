package services;
import entities.*;
import services.IO.Audit;
import services.IO.ClientIOService;
import services.IO.OrganizerIOService;
import util.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import validators.UserValidator;

public class UserService implements Auth {
    protected List<User> users;
    protected User currentUser;
    private static UserService userService;
    private UserService(){
        users = new ArrayList<>();
        users.addAll(ClientIOService.getClientIOService().load());
        users.addAll(OrganizerIOService.getOrganizerIOService().load());
        currentUser = null;
    }
    public void close()
    {
        List<Client> clients = users.stream()
                .filter(i -> i instanceof Client).map(i -> (Client) i)
                .collect(Collectors.toList());
        List<Organizer> organizers = users.stream()
                .filter(i -> i instanceof Organizer).map(i -> (Organizer) i)
                .collect(Collectors.toList());
        ClientIOService.getClientIOService().save(clients);
        OrganizerIOService.getOrganizerIOService().save(organizers);
    }
    public static UserService getUserService()
    {
        if(userService == null)
            userService = new UserService();
        return userService;
    }
    public void logOff()
    {
        Audit.getAudit().addAction("logOff");
        if(currentUser != null)
        {currentUser = null;
            System.out.println("You logged off!");}
        else
            System.out.println("You are already logged off!");
    }
    public void signIn() throws MyException
    {
        Audit.getAudit().addAction("signIn");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Username: ");
        String username = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();

        for(int i = 0; i < users.size(); i ++)
        {
            User user = users.get(i);
            if(user.getUsername().equals(username) && user.getPassword().compareTo(password) == 0)
            {
                 currentUser = user;
                 break;
            }
        }
        if(currentUser != null)
            System.out.println("Welcome back " + currentUser.getFullname() + "!");
        else
            throw new MyException("Something went wrong! We can't log you in!");
    }

    public User getCurrentUser(){return currentUser;}

    public void showUsers()
    {
        Audit.getAudit().addAction("showUsers");
        System.out.println("\nTHE USERS ARE: ");
        for(User user: users)
        {
            System.out.println(user.toString());
        }
        System.out.println();

    }
    public void register(User user) throws MyException
    {
        Audit.getAudit().addAction("register");
        try {
            UserValidator validator = new UserValidator();
            String errors = validator.validate(user.getPassword(), user.getEmail());
            if (errors.length() > 0) {
                throw new MyException("The registration can't be completed:  " + errors);
            }
            if (users.contains(user))
                throw new AlreadyRegisteredUser();
            for (User value : users) {
                if (user.getEmail().equals(value.getEmail()))
                    throw new UniqueUserEmails();
            }
            users.add(user);
            System.out.println("You were successfully registered as a/an " + user.getClass().getName() + "! Welcome "
                    + user.getFullname() + "!");

        }
        catch(MyException e)
        {
            throw e;
        }
    }
    public void viewHistory(User user) throws MyException {

        Audit.getAudit().addAction("viewHistory");
        if(user instanceof Client) {
            Client client = (Client) user;
            System.out.println("Your order history is: ");
            List<Order> clientOrders = client.getOrders();
            Comparator<Order> orderComparatorByDate = (o1, o2) -> o1.getDatePurchased()
                    .compareTo(o2.getDatePurchased());
            Collections.sort(clientOrders, orderComparatorByDate);
            double totalSum = 0;
            for (Order clientOrder : clientOrders) {

                totalSum += clientOrder.getTotalPrice();
                System.out.println(clientOrder.toString());
            }
            System.out.println("The cumulative price is " + totalSum + "$.\n");
        }
        else throw new PermissionDenied();

    }
    public void changeCreditCard(User user, CreditCard card)
    {
        Audit.getAudit().addAction("changeCreditCard");
        if(user instanceof Client) {
            Client client = (Client)user;
            client.setCreditCard(card);
            System.out.println("Your account was successfully modified!");
            System.out.println(client.getCreditCard());
        }
        else
            System.out.println("You don't have a credit card!");
    }
    public void viewCalendar(User user) throws MyException
    {
        Audit.getAudit().addAction("viewCalendar");
        if(user instanceof Client) {

            Client client = (Client) user;
            List<Order> orders = client.getOrders();
            List<Ticket> tickets = new ArrayList<Ticket>();
            for (Order order : orders) {
                tickets.addAll(order.getTickets());
            }
            List<Event> monthEvents = new ArrayList<Event>();
            LocalDate initial = LocalDate.now();
            LocalDate firstDay = initial.withDayOfMonth(1);
            LocalDate lastDay = initial.withDayOfMonth(initial.lengthOfMonth());
            for (Ticket ticket : tickets) {
                LocalDate start = ticket.getEvent().getStartTime().toLocalDate();
                LocalDate finish = ticket.getEvent().getEndTime().toLocalDate();
                if (ticket.getStatus() == TicketStatus.AVAILABLE && start.compareTo(firstDay) >=0  &&
                        lastDay.compareTo(finish) >= 0)
                    monthEvents.add(ticket.getEvent());

            }
            monthEvents.sort(new EventSorterAscByStartDate());
            System.out.println("Events this month: ");
            for (Event monthEvent : monthEvents) System.out.println(monthEvent.toString());
        }
        else throw new PermissionDenied();
    }
    public Organizer getOrganizerById(int id)
    {
        Optional<User> organizer = users.stream()
                .filter(elem -> elem.getId() == id && elem instanceof Organizer)
                .findFirst();
        return (Organizer) organizer.orElse(null);
    }
    public Client getClientById(int id)
    {
        Optional<User> client = users.stream()
                .filter(elem -> elem.getId() == id && elem instanceof Client)
                .findFirst();
        return (Client) client.orElse(null);
    }
}