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
    public boolean validateEventName(Event event)
    {
        // numele unui eveniment incepe cu litera mare
        // trebuie sa contina cel putin 3 litere mici
        // poate contine spatii si cifre
        String regex = "[A-Z]\\s*([A-Z]*[a-z]{3,}\\s*[0-9]*)*";
        return event.getName().matches(regex);
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
