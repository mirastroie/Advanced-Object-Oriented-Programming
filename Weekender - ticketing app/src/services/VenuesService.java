package services;
import java.util.ArrayList;
import java.util.List;

import util.MyException;
import entities.Venue;
import validators.VenueValidator;

public class VenuesService {

    private List<Venue> venues;
    private static VenuesService venuesService;
    private VenuesService(){
        venues = new ArrayList<Venue>();
    }
    public static VenuesService getVenuesService()
    {
        if(venuesService == null)
            venuesService = new VenuesService();
        return venuesService;
    }
    public void showVenues()
    {
        System.out.println("THE VENUES ARE:");
        for(int i = 0; i < venues.size(); i ++)
            System.out.println((i + 1) + ". " + venues.get(i).toString());
        System.out.println();
    }
    public void addVenue(Venue venue)
    {
        try {
            VenueValidator validator = new VenueValidator();
            String errors = validator.validateVenue(venue);
            if (errors.length() > 0) {
                throw new MyException(errors);
            }
            venues.add(venue);
        }catch(MyException e)
        {
            System.out.println("The venue could not be added: " + e);
        }
    }
    public void removeVenue(int venueId)
    {
        for(int i = 0; i < venues.size(); i++) {
            Venue venue = venues.get(i);
            if (venue.getId() == venueId)
            { venues.remove(venue);break;}
        }
    }



}
