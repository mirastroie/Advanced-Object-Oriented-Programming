package services;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

import services.IO.Audit;
import services.IO.VenuesIOService;
import util.MyException;
import entities.Venue;
import validators.VenueValidator;

public class VenuesService {

    private LinkedList<Venue> venues;
    private static VenuesService venuesService;
    private VenuesService(){
        venues = new LinkedList<>(VenuesIOService.getVenuesIOService().load());

    }
    public void close()
    {
        VenuesIOService.getVenuesIOService().save(new ArrayList<>(venues));
    }
    public static VenuesService getVenuesService()
    {
        if(venuesService == null)
            venuesService = new VenuesService();
        return venuesService;
    }
    public LinkedList<Venue> getVenues()
    {
        return venues;
    }
    public void showVenues()
    {
        Audit.getAudit().addAction("showVenues");
        System.out.println("THE VENUES ARE:");
        for(int i = 0; i < venues.size(); i ++)
            System.out.println((i + 1) + ". " + venues.get(i).toString());
        System.out.println();
    }
    public Venue getVenueById(int id)
    {
        Optional<Venue> venue = venues.stream().filter(elem -> elem.getId() == id).findFirst();
        return venue.orElse(null);
    }
    public void addVenue(Venue venue)
    {
        Audit.getAudit().addAction("addVenue");
        try {
            VenueValidator validator = new VenueValidator();
            String errors = validator.validateVenue(venue);
            if (errors.length() > 0) {
                throw new MyException(errors);
            }
            if(!venues.contains(venue))
                  venues.addFirst(venue);
            else
                throw new MyException("Venue already added!");
        }catch(MyException e)
        {
            System.out.println("The venue could not be added: " + e);
        }
    }
    public void removeVenue(int venueId)
    {
        Audit.getAudit().addAction("removeVenue");
        EventService eventService = EventService.getEventService();
        Boolean deleted = false;
        if(!eventService.isCurrentVenue(venueId)) {
            for (int i = 0; i < venues.size(); i++) {
                Venue venue = venues.get(i);
                if (venue.getId() == venueId) {
                    venues.remove(venue);
                    deleted = true;
                    break;
                }
            }
            if(deleted)
                System.out.println("The venue has been removed!");
            else
                System.out.println("Can't find the venue!");
        }
        else
            System.out.println("The venue can't be removed!");
    }

}
