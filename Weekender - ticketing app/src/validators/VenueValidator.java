package validators;

import entities.Venue;
import util.Error;

public class VenueValidator {

    static public int MAX_CAPACITY = 150000;
    public boolean validateCapacity(int capacity)
    {
        if(capacity <= 0 || capacity > MAX_CAPACITY)
            return false;
        return true;
    }
    public boolean validateDescription(String description)
    {
        if(description.length() > 2000 || description.length() < 5)
            return false;
        return true;
    }
    public boolean validateZIP(String ZIP)
    {
        String regex = "^\\d{5}(?:[-\\s]\\d{4})?$";
        return ZIP.matches(regex);
    }
    public boolean validateZone(String zone)
    {
        String regex = "([a-zA-Z]{2,}\\s*)+";
        return zone.matches(regex);
    }
    public String validateVenue(Venue venue)
    {
        StringBuilder result = new StringBuilder();
        if(!validateCapacity(venue.getCapacity()))
            result.append(Error.INVALID_CAPACITY + ", ");
        if(!validateDescription(venue.getDescription()))
            result.append(Error.INVALID_VENUE_DESCRIPTION +  ", ");
        if(!validateZIP(venue.getZIP()))
            result.append(Error.INVALID_ZIP + ", ");
        if(!validateZone(venue.getRegion()))
            result.append(Error.INVALID_REGION + ", ");
        if(!validateZone(venue.getCountry()))
            result.append(Error.INVALID_COUNTRY);
        return result.toString();

    }
}
