package validators;

import entities.Event;
import entities.Venue;
import util.Error;

import java.util.List;

import java.time.LocalDateTime;

public class EventValidator {


    public String validateEvent(Event event, List<Event> events)
    {
        StringBuilder result = new StringBuilder();

        if(!validateCapacityEvent(event))
            result.append(Error.INVALID_CAPACITY + ", ");
        if(!validateAvailability(event,events))
            result.append(Error.VENUE_NOT_AVAILABLE +  ", ");

        return result.toString();

    }
    public boolean validateCapacityEvent(Event event)
    {
        return event.getAvailableSeats() <= event.getVenue().getCapacity();
    }
    public boolean validateAvailability(Event event, List<Event> events)
    {

        LocalDateTime start = event.getStartTime();
        LocalDateTime finish = event.getEndTime();
        Venue venue = event.getVenue();
        for (Event existingEvent : events) {
            LocalDateTime existingStart = existingEvent.getStartTime();
            LocalDateTime existingFinish = existingEvent.getEndTime();

            //nu e okay daca evenimentul pe care vreau sa il adaug se suprapune cu un alt eveniment
            //care se desfasoara in acelasi venue
            if (venue.equals(existingEvent.getVenue()) && start.isBefore(existingFinish) && finish.isAfter(existingStart))
                return false;
        }
        return true;
    }
}
